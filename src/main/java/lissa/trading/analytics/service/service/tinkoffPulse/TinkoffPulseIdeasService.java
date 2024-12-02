package lissa.trading.analytics.service.service.tinkoffPulse;

import lissa.trading.analytics.service.client.pulse.TinkoffPulseClient;
import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.StockIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.StockIdeasResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ideasService")
@Slf4j
@RequiredArgsConstructor
public class TinkoffPulseIdeasService implements TinkoffPulseService {

    @Value("${integration.pulse.idea-page-url}")
    private String pulseIdeaPageUrl;

    private final TinkoffPulseClient tinkoffPulseClient;

    @Override
    public List<ResponseDto> getData(List<String> tickers) {
        log.info("Getting ideas data from Tinkoff Pulse");
        List<ResponseDto> responseDtoList = new ArrayList<>();

        for (String ticker : tickers) {
            List<StockIdeaDto> ideasDtoList = tinkoffPulseClient
                    .getStockIdeas(ticker.toUpperCase())
                    .getPayload()
                    .getIdeas();

            for (StockIdeaDto ideasDto : ideasDtoList) {
                ideasDto.setUrl(pulseIdeaPageUrl + ideasDto.getId());
            }

            responseDtoList.add(new StockIdeasResponseDto(ideasDtoList));
        }
        return responseDtoList;
    }
}
