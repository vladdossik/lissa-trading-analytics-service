package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.client.finam.FinamClient;
import lissa.trading.analytics.service.client.tinkoff.dto.CompanyNamesDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffTokenDto;
import lissa.trading.analytics.service.client.tinkoff.feign.StockServiceClient;
import lissa.trading.analytics.service.dto.NewsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("finamService")
@RequiredArgsConstructor
public class FinamNewsService implements NewsService {

    private final FinamClient finamClient;
    private final NewsXmlParser newsXmlParser;
    private final StockServiceClient stockServiceClient;
    @Value("${security.tinkoff.token}")
    private String tinkoffApiToken;

    @Override
    public NewsResponseDto getNews(List<String> tickers) {
        setTinkoffApiToken();
        CompanyNamesDto keywords = stockServiceClient.getCompanyNamesByTickers(tickers);
        log.info("Company names: {}", keywords);

        if (keywords.getNames().isEmpty()) {
            log.info("There is no keywords in entry, method is closing");
            return new NewsResponseDto(List.of());
        }

        NewsResponseDto unfilteredNews = newsXmlParser.toNewsDto(finamClient.getFinamRssFeed());
        log.info("Requesting to Tinkoff-service for company names by tickers");
        NewsResponseDto filteredNews = NewsDataProcessor.filterNewsByKeywords(unfilteredNews, keywords.getNames());
        return NewsDataProcessor.removeHtmlTagsFromText(filteredNews);
    }

    private void setTinkoffApiToken() {
        log.info("Requesting tinkoff-api-service for set tinkoff-token");
        stockServiceClient.setTinkoffToken(new TinkoffTokenDto(tinkoffApiToken));
    }
}
