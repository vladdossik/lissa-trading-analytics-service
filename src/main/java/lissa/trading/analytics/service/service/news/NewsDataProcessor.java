package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.dto.NewsDto;
import lissa.trading.analytics.service.dto.NewsResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
public class NewsDataProcessor {
    public static NewsResponseDto filterNewsByKeywords(NewsResponseDto news, List<String> keywords) {
        log.info("Filtering news by keywords: {}", keywords);
        List<NewsDto> filteredItems = news.getItems()
                .stream()
                .filter(item -> {
                    String description = item.getDescription().toLowerCase();
                    return keywords.stream()
                            .anyMatch(keyword -> description.contains(keyword.toLowerCase()));
                })
                .toList();
        news.setItems(filteredItems);

        log.info("Matched news count: {}", filteredItems.size());
        if (CollectionUtils.isEmpty(filteredItems)) {
            return new NewsResponseDto(Collections.emptyList());
        }
        return news;
    }

    public static NewsResponseDto removeHtmlTagsFromText(NewsResponseDto news) {
        log.info("Removing HTML tags from news data");
        List<NewsDto> cleanedItems = news.getItems()
                .stream()
                .map(item -> {
                    String cleanedDescription = item.getDescription()
                            .replaceAll("<[^>]+>", "")
                            .trim();
                    return new NewsDto(item.getTitle(), cleanedDescription, item.getPubDate(), item.getUrl());
                })
                .toList();
        news.setItems(cleanedItems);
        return news;
    }

}
