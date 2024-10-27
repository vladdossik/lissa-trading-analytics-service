package lissa.trading.analytics.service.service.indicator;

import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.exception.CalculationException;
import lissa.trading.analytics.service.exception.NotEnoughDataException;
import lissa.trading.analytics.service.exception.UnknownIntervalException;
import lissa.trading.analytics.service.model.Candle;
import lissa.trading.analytics.service.model.CandleInterval;
import lissa.trading.analytics.service.tinkoff.dto.CandlesDto;
import lissa.trading.analytics.service.tinkoff.dto.TinkoffCandlesRequestDto;
import lissa.trading.analytics.service.tinkoff.dto.TinkoffTokenDto;
import lissa.trading.analytics.service.tinkoff.feign.StockServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndicatorServiceImpl implements IndicatorService {
    private static final int PERIOD_RSI = 14;
    private final StockServiceClient stockServiceClient;
    @Value("security.tinkoff.token")
    private String tinkoffApiToken;

    @Override
    public IndicatorsDto getIndicators(TinkoffCandlesRequestDto candlesRequestDto) {
        log.info("Requesting tinkoff-api-service for candles data to tinkoff-service");
        CandlesDto candles = stockServiceClient.getCandles(candlesRequestDto);
        return calculateIndicators(candles.getCandles(), candlesRequestDto.getInterval());
    }

    private void setTinkoffApiToken() {
        log.info("Requesting tinkoff-api-service for set tinkoff-token");
        stockServiceClient.setTinkoffToken(new TinkoffTokenDto(tinkoffApiToken));
    }

    private IndicatorsDto calculateIndicators(List<Candle> candles, CandleInterval interval) {
        final int periodMaIndicators = getMaPeriod(interval, candles.size());

        IndicatorsDto indicatorsDto = new IndicatorsDto();
        indicatorsDto.setIndicatorSMA(calculateSma(candles, periodMaIndicators));
        indicatorsDto.setIndicatorEMA(calculateEma(candles, periodMaIndicators));
        indicatorsDto.setIndicatorRSI(calculateRsi(candles));
        log.info("Indicators were calculated: {}", indicatorsDto);
        return indicatorsDto;
    }

    private Double calculateRsi(List<Candle> candles) {
        log.info("Calculating RSI indicator");
        int candlesSize = candles.size();
        if (candlesSize < PERIOD_RSI + 1) {
            log.info("Failed to calculate RSI, not enough data! Candles size: {}, period: {}", candles.size(),
                    PERIOD_RSI);
            throw new NotEnoughDataException("Not enough candles data for calculate RSI");
        }

        double gain = 0.0;
        double loss = 0.0;

        for (int i = candlesSize - PERIOD_RSI; i < candlesSize; i++) {
            double change = candles.get(i).getClose() - candles.get(i - 1).getClose();
            gain += (change > 0) ? change : 0;
            loss += (change <= 0) ? -change : 0;
        }

        gain /= PERIOD_RSI;
        loss /= PERIOD_RSI;

        double rs = (loss == 0) ? Double.POSITIVE_INFINITY : gain / loss;

        return 100 - (100 / (1 + rs));
    }

    private Double calculateEma(List<Candle> candles, int period) {
        log.info("Calculating EMA indicator");

        double multiplier = 2.0 / (period + 1);

        double sum = candles.stream()
                .limit(period)
                .mapToDouble(Candle::getClose)
                .sum();

        double ema = sum / period;

        for (int i = period; i < candles.size(); i++) {
            double close = candles.get(i).getClose();
            ema = ((close - ema) * multiplier) + ema;
        }
        return ema;
    }

    public Double calculateSma(List<Candle> candles, int period) {
        log.info("Calculating SMA indicator");

        double sum = candles.stream()
                .skip(candles.size() - period)
                .mapToDouble(Candle::getClose)
                .sum();

        return sum / period;
    }

    private int getMaPeriod(CandleInterval interval, int candlesSize) {
        log.info("Choosing period for calculating MA indicators by interval {}", interval);
        int period;
        switch (interval) {
            case CANDLE_INTERVAL_5_MIN, CANDLE_INTERVAL_HOUR -> period = 20;
            case CANDLE_INTERVAL_DAY -> period = 50;
            case CANDLE_INTERVAL_WEEK -> period = 100;
            case CANDLE_INTERVAL_MONTH -> period = 200;
            default -> throw new UnknownIntervalException("Incorrect interval chosen: " + interval);
        }
        if (candlesSize < period) {
            log.error("Failed to calculate MA indicator. Not enough data - candles size: {}, period: {}", candlesSize,
                    period);
            throw new NotEnoughDataException("Not enough data to calculate SMA");
        }
        log.info("Chosen period is {}", period);
        return period;
    }

}
