package lissa.trading.analytics.service.service.indicator;

import lissa.trading.analytics.service.client.tinkoff.dto.CandlesDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffCandlesRequestDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffTokenDto;
import lissa.trading.analytics.service.client.tinkoff.feign.StockServiceClient;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.exception.CandlesNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndicatorServiceImpl implements IndicatorService {

    @Value("${security.tinkoff.token}")
    private String tinkoffApiToken;

    private final StockServiceClient stockServiceClient;
    private final IndicatorCalculator indicatorCalculator;

    @Override
    public IndicatorsDto getIndicators(TinkoffCandlesRequestDto candlesRequestDto) {
        setTinkoffApiToken();
        log.info("Requesting tinkoff-api-service for candles data to tinkoff-service");
        CandlesDto candles = stockServiceClient.getCandles(candlesRequestDto);
        if (candles == null || CollectionUtils.isEmpty(candles.getCandles())) {
            log.info("No candles found for requested data");
            throw new CandlesNotFoundException("No candles found for requested data");
        }
        return indicatorCalculator.calculateIndicators(candles.getCandles(), candlesRequestDto.getInterval());
    }

    private void setTinkoffApiToken() {
        log.info("Requesting tinkoff-api-service for set tinkoff-token");
        stockServiceClient.setTinkoffToken(new TinkoffTokenDto(tinkoffApiToken));
    }
}
