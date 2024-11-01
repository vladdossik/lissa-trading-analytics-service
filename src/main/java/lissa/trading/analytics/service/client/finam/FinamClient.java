package lissa.trading.analytics.service.client.finam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinamClient {
    private final RestTemplate restTemplate;
    @Value("${integration.rest.finam-news-rss-url}")
    private String finamRssUrl;

    public String getFinamRssFeed() {
        log.info("Requesting to finam-api with url: {}", finamRssUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(finamRssUrl, String.class);
        return response.getBody();
    }

}
