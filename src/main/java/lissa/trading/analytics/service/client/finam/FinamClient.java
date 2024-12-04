package lissa.trading.analytics.service.client.finam;

import lissa.trading.analytics.service.exception.FinamClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinamClient {

    private final WebClient finamWebClient;

    public String getFinamRssFeed() {
        log.info("Requesting to finam.ru rss feed...");
        String rssFeed = finamWebClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    log.error("Failed to retrieve news data from finam.ru rss feed", error);
                    throw new FinamClientException("Failed to retrieve news data from finam.ru rss feed");
                })
                .block();

        if (rssFeed == null) {
            log.error("Fetched null from finam.ru rss feed");
            throw new FinamClientException("Fetched null from finam.ru rss feed");
        }
        return rssFeed;
    }
}
