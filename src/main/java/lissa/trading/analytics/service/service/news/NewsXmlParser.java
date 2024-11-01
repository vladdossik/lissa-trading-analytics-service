package lissa.trading.analytics.service.service.news;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lissa.trading.analytics.service.dto.NewsResponseDto;
import lissa.trading.analytics.service.dto.RssFeedDto;
import lissa.trading.analytics.service.exception.XmlParsingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.StringReader;

@Slf4j
@Component
public class NewsXmlParser {
    public NewsResponseDto toNewsDto(String xml) {
        log.info("Trying to parse XML document");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RssFeedDto.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            RssFeedDto rssFeed = (RssFeedDto) unmarshaller.unmarshal(new StringReader(xml));
            log.info("XML document parsed successfully");
            return new NewsResponseDto(rssFeed.getChannel().getItems());
        } catch (JAXBException e) {
            log.error("Error while parsing XML document");
            throw new XmlParsingException("Error while parsing XML", e);
        }
    }
}
