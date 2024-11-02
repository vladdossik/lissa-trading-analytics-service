package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.dto.NewsDto;
import lissa.trading.analytics.service.dto.NewsResponseDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class NewsDataHandler {
    public static NewsResponseDto filterNewsByKeywords(NewsResponseDto news, List<String> keywords){
        log.info("Filtering news by keywords: {}", keywords);
        List<NewsDto> filteredItems = news.getItems().stream()
                .filter(item -> keywords.stream()
                        .anyMatch(keyword -> item.getDescription()
                                .toLowerCase()
                                .contains(keyword.toLowerCase()))
                )
                .toList();
        news.setItems(filteredItems);
        log.info("Matched news count: {}", filteredItems.size());
        return news;
    }

    public static NewsResponseDto removeHtmlTagsFromText(NewsResponseDto news){
        log.info("Removing HTML tags from news data");
        List<NewsDto> cleanedItems = news.getItems().stream()
                .map(item -> {
                    String cleanedDescription = item.getDescription().replaceAll("<[^>]+>", "").trim();
                    return new NewsDto(item.getTitle(), cleanedDescription, item.getPubDate(), item.getUrl());
                })
                .toList();
        news.setItems(cleanedItems);
        return news;
    }

}
