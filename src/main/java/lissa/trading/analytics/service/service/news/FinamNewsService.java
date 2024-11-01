package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.client.finam.FinamClient;
import lissa.trading.analytics.service.dto.NewsDto;
import lissa.trading.analytics.service.dto.NewsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinamNewsService implements NewsService {
    private final FinamClient finamClient;
    private final NewsXmlParser newsXmlParser;

    @Override
    public NewsResponseDto getNews(List<String> keywords) {
        NewsResponseDto unfilteredNews = newsXmlParser.toNewsDto(finamClient.getFinamRssFeed());
        return filterNewsByKeywords(unfilteredNews, keywords);
    }

    public NewsResponseDto filterNewsByKeywords(NewsResponseDto news, List<String> keywords){
        log.info("Filtering news by keywords: {}", keywords);
        List<NewsDto> filteredItems = news.getItems().stream()
                .filter(item -> keywords.stream()
                        .anyMatch(keyword -> item.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                )
                .toList();
        news.setItems(filteredItems);
        log.info("Matched news count: {}", filteredItems.size());
        return news;
    }
}
