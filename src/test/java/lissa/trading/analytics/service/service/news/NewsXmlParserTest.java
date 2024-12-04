package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.dto.NewsResponseDto;
import lissa.trading.analytics.service.exception.XmlParsingException;
import lissa.trading.analytics.service.service.AbstractInitialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class NewsXmlParserTest extends AbstractInitialization {
    @Autowired
    private NewsXmlParser newsXmlParser;

    @Test
    void toNewsDto_Correct() {
        NewsResponseDto news = newsXmlParser.toNewsDto(finamRssFeed);
        assertNotNull(news);
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