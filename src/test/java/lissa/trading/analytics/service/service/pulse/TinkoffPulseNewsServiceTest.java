package lissa.trading.analytics.service.service.pulse;

import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.FullNewsDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.PayloadNewsDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.StockNewsResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TinkoffPulseNewsServiceTest extends PulseBaseTest {

    private static final String NEWS_PAGE_URL = "https://lissa-trading.ru/news/";

    @BeforeEach
    void setUp() {
        super.setUp();
        ReflectionTestUtils.setField(tinkoffPulseNewsService, "newsPageUrl", NEWS_PAGE_URL);
    }

    @Test
    void getData_Correct() {
        final String expectedPageUrl = NEWS_PAGE_URL + fullNewsDto.getPayload().getItems().get(0).getOwner().getNickname()
                        + "/" + fullNewsDto.getPayload().getItems().get(0).getId();

        when(tinkoffPulseClientMock.getStockNews()).thenReturn(fullNewsDto);

        List<String> tickers = List.of("SBER");
        List<ResponseDto> response = tinkoffPulseNewsService.getData(tickers);

        assertNotNull(response);
        assertEquals(1, response.size());

        StockNewsResponseDto stockNewsResponseDto = (StockNewsResponseDto) response.get(0);
        assertNotNull(stockNewsResponseDto);
        assertEquals(1, stockNewsResponseDto.getItems()
                .size());
        assertEquals("SBER", stockNewsResponseDto.getItems().get(0)
                .getContent()
                .getInstruments().get(0)
                .getTicker());

        assertEquals(expectedPageUrl, stockNewsResponseDto.getItems().get(0).getUrl());
    }

    @Test
    void getData_EmptyResponse() {
        FullNewsDto emptyFullNewsDto = new FullNewsDto(new PayloadNewsDto(Collections.emptyList()));
        when(tinkoffPulseClientMock.getStockNews()).thenReturn(emptyFullNewsDto);

        List<String> tickers = List.of("SBER");

        List<ResponseDto> response = tinkoffPulseNewsService.getData(tickers);

        assertNotNull(response);
        assertEquals(1, response.size());

        StockNewsResponseDto stockNewsResponseDto = (StockNewsResponseDto) response.get(0);
        assertNotNull(stockNewsResponseDto);
        assertTrue(stockNewsResponseDto.getItems().isEmpty());
    }

    @Test
    void getData_NewsByTickersNotFound() {
        when(tinkoffPulseClientMock.getStockNews()).thenReturn(fullNewsDto);

        List<String> tickers = List.of("FAKE");

        List<ResponseDto> response = tinkoffPulseNewsService.getData(tickers);

        assertNotNull(response);
        assertEquals(1, response.size());

        StockNewsResponseDto stockNewsResponseDto = (StockNewsResponseDto) response.get(0);
        assertNotNull(stockNewsResponseDto);
        assertTrue(stockNewsResponseDto.getItems().isEmpty());
    }

}
