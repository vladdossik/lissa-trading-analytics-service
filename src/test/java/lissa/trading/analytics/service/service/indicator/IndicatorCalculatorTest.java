package lissa.trading.analytics.service.service.indicator;

import lissa.trading.analytics.service.dto.CandleInterval;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.exception.NotEnoughDataException;
import lissa.trading.analytics.service.service.AbstractInitialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class IndicatorCalculatorTest extends AbstractInitialization {
    @Autowired
    private IndicatorCalculator calculator;

    @Test
    void calculateIndicators_Correct() {
        IndicatorsDto indicators = calculator.calculateIndicators(candlesDto.getCandles(),
                CandleInterval.CANDLE_INTERVAL_DAY);
        assertNotNull(indicators);
        assertNotNull(indicators.getIndicatorEMA());
        assertNotNull(indicators.getIndicatorSMA());
        assertNotNull(indicators.getIndicatorRSI());
        assertTrue(indicators.getIndicatorRSI() > 0);
        assertTrue(indicators.getIndicatorSMA() > 0);
        assertTrue(indicators.getIndicatorEMA() > 0);
        assertEquals(indicators, indicatorsDto);
    }

    @Test
    void calculateIndicators_NotEnoughDataException() {
        assertThrows(NotEnoughDataException.class, () -> {
            calculator.calculateIndicators(candlesDto.getCandles().subList(0,49), CandleInterval.CANDLE_INTERVAL_DAY);
        });
    }

}
