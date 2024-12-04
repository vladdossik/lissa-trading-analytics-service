package lissa.trading.analytics.service.service.tinkoffPulse;

import lissa.trading.analytics.service.client.pulse.TinkoffPulseClient;
import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandInfoDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("brandInfoService")
@RequiredArgsConstructor
@Slf4j
public class TinkoffPulseBrandInfoService implements TinkoffPulseService {

    private final TinkoffPulseClient tinkoffPulseClient;

    @Override
    public List<ResponseDto> getData(List<String> tickers) {
        log.info("Getting brands info data from Tinkoff Pulse");
        List<ResponseDto> responseDtos = new ArrayList<>();
        for (String ticker : tickers) {
            BrandInfoDto brandInfoDto = processBrandsByTicker().get(ticker.toUpperCase());
            if (brandInfoDto != null) {
                responseDtos.add(new BrandInfoResponseDto(brandInfoDto));
            } else {
                responseDtos.add(new BrandInfoResponseDto());
            }
        }
        return responseDtos;
    }

    private Map<String, BrandInfoDto> processBrandsByTicker() {
        Map<String, BrandInfoDto> brandsByTicker = new HashMap<>();

        for (BrandInfoDto brand : tinkoffPulseClient.getBrandsInfo().getPayload().getBrands()) {
            for (String ticker : brand.getTickers()) {
                brandsByTicker.put(ticker, brand);
            }
        }
        return brandsByTicker;
    }
}
