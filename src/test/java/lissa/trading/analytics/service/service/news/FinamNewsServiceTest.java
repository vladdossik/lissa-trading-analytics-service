package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.client.finam.FinamClient;
import lissa.trading.analytics.service.client.tinkoff.dto.CompanyNamesDto;
import lissa.trading.analytics.service.client.tinkoff.feign.StockServiceClient;
import lissa.trading.analytics.service.dto.NewsResponseDto;
import lissa.trading.analytics.service.service.AbstractInitialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class FinamNewsServiceTest extends AbstractInitialization {

    @InjectMocks
    protected FinamNewsService FinamNewsService;

    @Mock
    protected FinamClient finamClientMock;

    @Mock
    protected StockServiceClient stockServiceClientMock;

    @Mock
    protected NewsXmlParser newsXmlParserMock;

    @Mock
    protected NewsDataProcessor newsDataProcessorMock;

    @BeforeEach
    void setToken() {
        doNothing().when(stockServiceClientMock).setTinkoffToken(any());
    }

    @Test
    void getNews_Success() {
        List<String> tickers = List.of("GAZP");
        when(stockServiceClientMock.getCompanyNamesByTickers(tickers)).thenReturn(companyNamesDto);
        when(finamClientMock.getFinamRssFeed()).thenReturn(finamRssFeed);
        when(newsXmlParserMock.toNewsDto(finamRssFeed)).thenReturn(newsResponseDto);
        try (MockedStatic<NewsDataProcessor> processor = Mockito.mockStatic(NewsDataProcessor.class)) {
            processor.when(() -> NewsDataProcessor.filterNewsByKeywords(newsResponseDto, companyNamesDto.getNames()))
                    .thenReturn(filteredNewsByCompanies);

            processor.when(() -> NewsDataProcessor.removeHtmlTagsFromText(filteredNewsByCompanies))
                    .thenReturn(newsWithoutHtml);

            NewsResponseDto result = FinamNewsService.getNews(tickers);

            assertNotNull(result);
            assertEquals(result.getItems().size(), 1);
            assertEquals(newsWithoutHtml, result);
        }
    }

    @Test
    void getNews_NoCompaniesFound_EmptyList() {
        List<String> tickers = List.of("LSSA");
        when(stockServiceClientMock.getCompanyNamesByTickers(tickers)).thenReturn(
                new CompanyNamesDto(Collections.emptyList()));
        NewsResponseDto result = FinamNewsService.getNews(tickers);
        NewsResponseDto expected = new NewsResponseDto(Collections.emptyList());
        assertNotNull(result);
        assertEquals(result, expected);
    }

    @Test
    void getNews_NewsNotFound_EmptyList() {
        List<String> tickers = List.of("FAKETICKER");
        when(stockServiceClientMock.getCompanyNamesByTickers(tickers))
                .thenReturn(new CompanyNamesDto(List.of("FAKEDATA")));
        when(finamClientMock.getFinamRssFeed()).thenReturn(finamRssFeed);
        when(newsXmlParserMock.toNewsDto(finamRssFeed)).thenReturn(newsResponseDto);
        try (MockedStatic<NewsDataProcessor> processor = Mockito.mockStatic(NewsDataProcessor.class)) {
            processor.when(() -> NewsDataProcessor.filterNewsByKeywords(newsResponseDto, List.of("FAKEDATA")))
                    .thenReturn(new NewsResponseDto(Collections.emptyList()));

            NewsResponseDto result = FinamNewsService.getNews(tickers);

            assertNotNull(result);
            assertEquals(result.getItems().size(), 0);
            assertEquals(result, new NewsResponseDto(Collections.emptyList()));
        }
    }

}