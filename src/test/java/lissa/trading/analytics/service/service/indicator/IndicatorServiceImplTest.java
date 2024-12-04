package lissa.trading.analytics.service.service.indicator;

import lissa.trading.analytics.service.client.tinkoff.dto.CandlesDto;
import lissa.trading.analytics.service.client.tinkoff.feign.StockServiceClient;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.exception.CandlesNotFoundException;
import lissa.trading.analytics.service.exception.NotEnoughDataException;
import lissa.trading.analytics.service.service.AbstractInitialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;

import static lissa.trading.analytics.service.dto.CandleInterval.CANDLE_INTERVAL_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class IndicatorServiceImplTest extends AbstractInitialization {

    @InjectMocks
    IndicatorServiceImpl indicatorService;

    @Mock
    StockServiceClient stockServiceClientMock;

    @Mock
    IndicatorCalculator indicatorCalculatorMock;

    @BeforeEach
    void setToken(){
        doNothing().when(stockServiceClientMock).setTinkoffToken(any());
    }

    @Test
    void getIndicators_Correct() {
        when(stockServiceClientMock.getCandles(any())).thenReturn(candlesDto);
        when(indicatorCalculatorMock.calculateIndicators(candlesDto.getCandles(), CANDLE_INTERVAL_DAY)).thenReturn(
                indicatorsDto);
        IndicatorsDto result = indicatorService.getIndicators(tinkoffCandlesRequestDto);
        assertNotNull(result);
        assertNotNull(result.getIndicatorEMA());
        assertNotNull(result.getIndicatorSMA());
        assertNotNull(result.getIndicatorRSI());
        assertTrue(result.getIndicatorRSI() > 0);
        assertTrue(result.getIndicatorSMA() > 0);
        assertTrue(result.getIndicatorEMA() > 0);
        assertEquals(indicatorsDto, result);
    }

    @Test
    void getIndicators_NotEnoughDataException() {
        when(stockServiceClientMock.getCandles(any()))
                .thenReturn(new CandlesDto(candlesDto.getCandles().subList(0, 13)));
        when(indicatorCalculatorMock.calculateIndicators(candlesDto.getCandles().subList(0, 13),
                CANDLE_INTERVAL_DAY)).thenThrow(NotEnoughDataException.class);

        assertThrows(NotEnoughDataException.class, () -> indicatorService.getIndicators(tinkoffCandlesRequestDto));
    }

    @Test
    void getIndicators_NoCandlesFoundException() {
        when(stockServiceClientMock.getCandles(any())).thenReturn(new CandlesDto(Collections.emptyList()));
        assertThrows(CandlesNotFoundException.class, () -> indicatorService.getIndicators(tinkoffCandlesRequestDto));
        verify(indicatorCalculatorMock, never()).calculateIndicators(any(), any());
    }


}
