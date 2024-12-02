package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.dto.NewsResponseDto;
import lissa.trading.analytics.service.exception.XmlParsingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NewsXmlParserTest extends BaseNewsTest {
    private NewsXmlParser newsXmlParser;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        newsXmlParser = new NewsXmlParser();
    }

    @Test
    void toNewsDto_Correct() {
        NewsResponseDto news = newsXmlParser.toNewsDto(finamRssFeed);
        assertNotNull(news);
        System.out.println("expected: " + newsResponseDto);
        System.out.println("my method: " + news);
        assertEquals(news, newsResponseDto);
    }

    @Test
    void toNewsDto_Null_ThrowsXmlParsingException() {
        assertThrows(XmlParsingException.class, () -> newsXmlParser.toNewsDto(null));
    }

    @Test
    void toNewsDto_EmptyList_EmptyObject() {
        NewsResponseDto news = newsXmlParser.toNewsDto("");
        assertNotNull(news);
        assertEquals(new NewsResponseDto(Collections.emptyList()), news);
    }
}