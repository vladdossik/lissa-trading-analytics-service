package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.dto.NewsResponseDto;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NewsDataProcessorTest extends BaseNewsTest {
    @Test
    void filterNewsByKeyword_Correct() {
        NewsResponseDto news = NewsDataProcessor.filterNewsByKeywords(newsResponseDto, List.of("Газпром"));
        assertNotNull(news);
        assertEquals(news, filteredNewsByCompanies);
    }

    @Test
    void filterNewsByKeyword_newsNotFound() {
        NewsResponseDto news = NewsDataProcessor.filterNewsByKeywords(newsResponseDto, List.of("Лисса-Трейдинг"));
        assertNotNull(news);
        assertEquals(news, new NewsResponseDto(Collections.emptyList()));
    }

}
