package lissa.trading.analytics.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig implements WebFluxConfigurer {

    @Value("${integration.rest.finam-news-rss-url}")
    private String finamRssUrl;

    private static final int MAX_IN_MEMORY_SIZE = 16 * 1024 * 1024;

    @Bean
    public WebClient finamWebClient() {
        return WebClient.builder()
                .baseUrl(finamRssUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                .exchangeStrategies(configureExchangeStrategies())
                .build();
    }

    @Bean
    public WebClient tinkoffPulseWebClient() {
        return WebClient.builder()
                .exchangeStrategies(configureExchangeStrategies())
                .build();
    }

    private ExchangeStrategies configureExchangeStrategies() {
        return ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(MAX_IN_MEMORY_SIZE))
                .build();
    }
}

