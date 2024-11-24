package lissa.trading.analytics.service.client.finam;

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
        log.info("Requesting to finam.ru rss lent...");
        return finamWebClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    log.error("Failed to retrieve news data from finam.ru rss lent");
                })
                .block();
    }
}
