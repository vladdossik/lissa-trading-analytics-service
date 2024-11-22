package lissa.trading.analytics.service.service.tinkoffPulse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lissa.trading.analytics.service.client.gpt.TinkoffPulseClient;
import lissa.trading.analytics.service.dto.TinkoffPulse.ideas.StockIdeasDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.ideas.StockIdeasResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ideasService")
@Slf4j
@RequiredArgsConstructor
public class TinkoffPulseIdeasService implements TinkoffPulseService<List<StockIdeasResponseDto>> {

    private final TinkoffPulseClient tinkoffPulseClient;

    @Override
    public List<StockIdeasResponseDto> getData(List<String> tickers) {
        log.info("Getting ideas data from Tinkoff Pulse");
        List<StockIdeasResponseDto> responseDtoList = new ArrayList<>();
        try {
            for (String ticker : tickers) {
                String json = tinkoffPulseClient.getStockIdeas(ticker);
                ObjectMapper mapper = new ObjectMapper();
                List<StockIdeasDto> ideasDtoList = mapper
                        .readValue(
                                mapper.readTree(json)
                                        .path("payload")
                                        .path("ideas")
                                        .toString(),
                                new TypeReference<>() {
                                });

                for (StockIdeasDto ideasDto : ideasDtoList) {
                    ideasDto.setUrl("https://www.tbank.ru/invest/ideas/" + ideasDto.getId());
                }

                responseDtoList.add(StockIdeasResponseDto.builder()
                        .ideas(ideasDtoList)
                        .message("Найдено " + ideasDtoList.size()
                                + " идей для инвестиций по тикеру: " + ticker)
                        .build());
            }
        } catch (Exception e) {
            log.error("Error parsing ideas", e);
            throw new RuntimeException(e);
        }
        return responseDtoList;
    }
}
