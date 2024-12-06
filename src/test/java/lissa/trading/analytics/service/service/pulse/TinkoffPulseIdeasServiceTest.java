package lissa.trading.analytics.service.service.pulse;

import lissa.trading.analytics.service.client.pulse.TinkoffPulseClient;
import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.BrokerIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.FullIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.PayloadIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.StockIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.StockIdeaFromPulseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.StockIdeasResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.TickerIdeaDto;
import lissa.trading.analytics.service.mapper.StockIdeaMapper;
import lissa.trading.analytics.service.service.AbstractInitialization;
import lissa.trading.analytics.service.service.tinkoffPulse.TinkoffPulseIdeasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TinkoffPulseIdeasServiceTest extends AbstractInitialization {
    private static final String PULSE_IDEA_URL = "https://lissa-trading.ru/ideas/";

    @InjectMocks
    private TinkoffPulseIdeasService tinkoffPulseIdeasService;

    @Mock
    private TinkoffPulseClient tinkoffPulseClientMock;

    @Mock
    private StockIdeaMapper stockIdeaMapper;

    private BrokerIdeaDto broker1;
    private BrokerIdeaDto broker2;
    private TickerIdeaDto sberTicker;
    private TickerIdeaDto gazpTicker;

    @BeforeEach
    void setUp() {
        broker1 = new BrokerIdeaDto("broker1", "Broker 1", 0.85);
        broker2 = new BrokerIdeaDto("broker2", "Broker 2", 0.9);

        sberTicker = new TickerIdeaDto("SBER", "Sberbank", 250.0);
        gazpTicker = new TickerIdeaDto("GAZP", "Gazprom", 300.0);

        ReflectionTestUtils.setField(tinkoffPulseIdeasService, "pulseIdeaPageUrl", PULSE_IDEA_URL);
    }

    @Test
    void getData_Correct() {
        List<String> tickers = List.of("SBER", "GAZP");

        StockIdeaFromPulseDto sberIdeaBeforeMapper = new StockIdeaFromPulseDto("1", "Idea 1", broker1,
                List.of(sberTicker), 10.0, null, "2024-01-01", "2024-12-31",
                100.0, 120.0, 0.2);

        StockIdeaFromPulseDto gazpIdeaBeforeMapper = new StockIdeaFromPulseDto("2", "Idea 2", broker2,
                List.of(gazpTicker), 15.0, null, "2024-01-01", "2024-12-31",
                150.0, 170.0, 0.15);

        StockIdeaDto mappedIdeaSber = new StockIdeaDto("1", "Idea 1", broker1, List.of(sberTicker),
                15.0, null, "2024-01-01", "2024-12-31", 150.0, 170.0, 0.15);

        StockIdeaDto mappedIdeaGazp = new StockIdeaDto("2", "Idea 2", broker2, List.of(gazpTicker),
                15.0, null, "2024-01-01", "2024-12-31", 150.0, 170.0, 0.15);


        when(tinkoffPulseClientMock.getStockIdeas("SBER")).thenReturn(new FullIdeaDto(
                new PayloadIdeaDto(List.of(sberIdeaBeforeMapper))));

        when(stockIdeaMapper.toStockIdeasForOtherServices(sberIdeaBeforeMapper)).thenReturn(mappedIdeaSber);

        when(tinkoffPulseClientMock.getStockIdeas("GAZP")).thenReturn(new FullIdeaDto(
                new PayloadIdeaDto(List.of(gazpIdeaBeforeMapper))));

        when(stockIdeaMapper.toStockIdeasForOtherServices(gazpIdeaBeforeMapper)).thenReturn(mappedIdeaGazp);


        List<ResponseDto> response = tinkoffPulseIdeasService.getData(tickers);

        assertNotNull(response);
        assertEquals(2, response.size());

        StockIdeasResponseDto responseDtoSber = (StockIdeasResponseDto) response.get(0);
        assertEquals("SBER", responseDtoSber.getIdeas().get(0).getTickers().get(0).getTicker());
        assertEquals("https://lissa-trading.ru/ideas/1", responseDtoSber.getIdeas().get(0).getUrl());

        StockIdeasResponseDto responseDtoGazp = (StockIdeasResponseDto) response.get(1);
        assertEquals("GAZP", responseDtoGazp.getIdeas().get(0).getTickers().get(0).getTicker());
        assertEquals("https://lissa-trading.ru/ideas/2", responseDtoGazp.getIdeas().get(0).getUrl());
    }

    @Test
    void getData_NoIdeasFoundForTicker() {
        List<String> tickers = List.of("SBER");

        when(tinkoffPulseClientMock.getStockIdeas("SBER")).thenReturn(new FullIdeaDto(new PayloadIdeaDto(List.of())));

        List<ResponseDto> response = tinkoffPulseIdeasService.getData(tickers);

        assertNotNull(response);
        assertEquals(1, response.size());
        StockIdeasResponseDto responseDtoSber = (StockIdeasResponseDto) response.get(0);
        assertEquals(0, responseDtoSber.getIdeas().size());
    }

    @Test
    void getData_SomeIdeasForOneTicker() {
        List<String> tickers = List.of("SBER");

        StockIdeaFromPulseDto sberIdea1BeforeMapper = new StockIdeaFromPulseDto("1", "Idea 1", broker1,
                List.of(sberTicker), 10.0, null, "2024-01-01", "2024-12-31",
                100.0, 120.0, 0.2);

        StockIdeaFromPulseDto sberIdea2BeforeMapper = new StockIdeaFromPulseDto("2", "Idea 2", broker2,
                List.of(sberTicker), 15.0, null, "2024-01-01", "2024-12-31",
                150.0, 170.0, 0.15);

        StockIdeaDto mappedIdeaSber1 = new StockIdeaDto("1", "Idea 1", broker1, List.of(sberTicker),
                15.0, null, "2024-01-01", "2024-12-31", 150.0, 170.0, 0.15);

        StockIdeaDto mappedIdeaSber2 = new StockIdeaDto("2", "Idea 2", broker2, List.of(sberTicker),
                15.0, null, "2024-01-01", "2024-12-31", 150.0, 170.0, 0.15);

        when(tinkoffPulseClientMock.getStockIdeas("SBER")).thenReturn(new FullIdeaDto(
                new PayloadIdeaDto(List.of(sberIdea1BeforeMapper,sberIdea2BeforeMapper))));

        when(stockIdeaMapper.toStockIdeasForOtherServices(sberIdea1BeforeMapper)).thenReturn(mappedIdeaSber1);
        when(stockIdeaMapper.toStockIdeasForOtherServices(sberIdea2BeforeMapper)).thenReturn(mappedIdeaSber2);

        List<ResponseDto> response = tinkoffPulseIdeasService.getData(tickers);

        assertNotNull(response);
        assertEquals(1, response.size());
        StockIdeasResponseDto responseDtoSber = (StockIdeasResponseDto) response.get(0);
        assertEquals(2, responseDtoSber.getIdeas().size());
        assertEquals("SBER", responseDtoSber.getIdeas().get(0).getTickers().get(0).getTicker());
        assertEquals("SBER", responseDtoSber.getIdeas().get(1).getTickers().get(0).getTicker());
    }

}
