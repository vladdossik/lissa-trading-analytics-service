package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.client.finam.FinamClient;
import lissa.trading.analytics.service.client.tinkoff.dto.CompanyNamesDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffTokenDto;
import lissa.trading.analytics.service.client.tinkoff.feign.StockServiceClient;
import lissa.trading.analytics.service.dto.NewsResponseDto;
import lissa.trading.analytics.service.exception.EmptyTickersListException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinamNewsService implements NewsService {

    @Value("${security.tinkoff.token}")
    private String tinkoffApiToken;

    private final FinamClient finamClient;
    private final NewsXmlParser newsXmlParser;
    private final StockServiceClient stockServiceClient;

    @Override
    public NewsResponseDto getNews(List<String> tickers) {
        if (tickers.isEmpty()){
            log.info("User requested news with empty tickers list");
            throw new EmptyTickersListException("The list of tickers can't be empty");
        }

        setTinkoffApiToken();
        CompanyNamesDto keywords = stockServiceClient.getCompanyNamesByTickers(tickers);
        log.info("Company names: {}", keywords);

        if (keywords.getNames().isEmpty()){
            log.info("There is no keywords in entry, method is closing");
            return new NewsResponseDto(List.of());
        }

        NewsResponseDto unfilteredNews = newsXmlParser.toNewsDto(finamClient.getFinamRssFeed());
        log.info("Requesting to Tinkoff-service for company names by tickers");
        NewsResponseDto filteredNews =  NewsDataProcessor.filterNewsByKeywords(unfilteredNews, keywords.getNames());
        return NewsDataProcessor.removeHtmlTagsFromText(filteredNews);
    }

    private void setTinkoffApiToken() {
        log.info("Requesting tinkoff-api-service for set tinkoff-token");
        stockServiceClient.setTinkoffToken(new TinkoffTokenDto(tinkoffApiToken));
    }
}
