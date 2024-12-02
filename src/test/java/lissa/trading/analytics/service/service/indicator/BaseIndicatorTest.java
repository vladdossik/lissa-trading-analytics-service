package lissa.trading.analytics.service.service.indicator;

import lissa.trading.analytics.service.client.tinkoff.dto.CandlesDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffCandlesRequestDto;
import lissa.trading.analytics.service.client.tinkoff.feign.StockServiceClient;
import lissa.trading.analytics.service.dto.CandleDto;
import lissa.trading.analytics.service.dto.CandleInterval;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BaseIndicatorTest {

    @Mock
    StockServiceClient stockServiceClientMock;

    @Mock
    IndicatorCalculator indicatorCalculatorMock;

    @InjectMocks
    IndicatorServiceImpl indicatorService;

    protected CandlesDto candlesDto;
    protected IndicatorsDto indicatorsDto;
    protected TinkoffCandlesRequestDto tinkoffCandlesRequestDto;

    @BeforeEach
    void setUp() {
        candlesDto = getCandlesDto();
        indicatorsDto = getIndicators();
        tinkoffCandlesRequestDto = getTinkoffCandlesRequestDto();
    }

    private CandlesDto getCandlesDto() {
        List<CandleDto> candles = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            double open = 100.0 + i;
            double close = open + 2.0;
            double high = close + 1.0;
            double low = open - 1.0;
            long volume = 1000 + i * 10;
            OffsetDateTime time = OffsetDateTime.now()
                    .minusDays(50 - i);
            boolean isComplete = i < 49;

            candles.add(new CandleDto(volume, open, close, high, low, time, isComplete));
        }

        return new CandlesDto(candles);
    }

    IndicatorsDto getIndicators() {
        return new IndicatorsDto(126.5, 126.5, 100.0);
    }

    TinkoffCandlesRequestDto getTinkoffCandlesRequestDto() {
        return new TinkoffCandlesRequestDto("uid", OffsetDateTime.now(), OffsetDateTime.now(),
                CandleInterval.CANDLE_INTERVAL_DAY);
    }
}
