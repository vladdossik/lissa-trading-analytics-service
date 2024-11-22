package lissa.trading.analytics.service.service.indicator;

import lissa.trading.analytics.service.client.tinkoff.dto.CandlesDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffCandlesRequestDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffTokenDto;
import lissa.trading.analytics.service.client.tinkoff.feign.StockServiceClient;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndicatorServiceImpl implements IndicatorService {
    private final StockServiceClient stockServiceClient;
    private final IndicatorCalculator indicatorCalculator;
    @Value("${security.tinkoff.token}")
    private String tinkoffApiToken;

    @Override
    public IndicatorsDto getIndicators(TinkoffCandlesRequestDto candlesRequestDto) {
        setTinkoffApiToken();
        log.info("Requesting tinkoff-api-service for candles data to tinkoff-service");
        CandlesDto candles = stockServiceClient.getCandles(candlesRequestDto);
        return indicatorCalculator.calculateIndicators(candles.getCandles(), candlesRequestDto.getInterval());
    }

    private void setTinkoffApiToken() {
        log.info("Requesting tinkoff-api-service for set tinkoff-token");
        stockServiceClient.setTinkoffToken(new TinkoffTokenDto(tinkoffApiToken));
    }
}
