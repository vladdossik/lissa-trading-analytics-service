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

    @Bean
    public WebClient finamWebClient() {
        final int maxInMemorySize = 16 * 1024 * 1024;
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(maxInMemorySize))
                .build();

        return WebClient.builder()
                .baseUrl(finamRssUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                .exchangeStrategies(exchangeStrategies)
                .build();
    }
}

