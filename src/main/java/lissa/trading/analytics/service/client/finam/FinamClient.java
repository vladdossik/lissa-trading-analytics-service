package lissa.trading.analytics.service.client.finam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinamClient {
    @Value("${integration.rest.finam-news-rss-url}")
    private String finamRssUrl;

    private final WebClient webClient;

    public String getFinamRssFeed() {
        log.info("Requesting to finam-api with url: {}", finamRssUrl);
        return webClient.get()
                .uri(finamRssUrl)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    log.error("Failed to retrieve news data from {}", finamRssUrl);
                })
                .block();
    }
}
