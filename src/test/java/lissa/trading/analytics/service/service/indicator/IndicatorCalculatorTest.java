package lissa.trading.analytics.service.service.indicator;

import lissa.trading.analytics.service.dto.CandleInterval;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.exception.NotEnoughDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IndicatorCalculatorTest extends BaseIndicatorTest {
    private IndicatorCalculator calculator;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        calculator = new IndicatorCalculator();
    }

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
