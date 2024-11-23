package lissa.trading.analytics.service.client.tinkoff.pulse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
@Component
public class TinkoffPulseClient {

    @Value("${security.tinkoff.pulse.stock-news-url}")
    private String pulseStocksNewsUrl;
    @Value("${security.tinkoff.pulse.brands-info-url}")
    private String pulseBrandsInfoUrl;
    @Value("${security.tinkoff.pulse.stock-ideas-url}")
    private String pulseStockIdeasUrl;

    @Qualifier("tinkoffPulseWebClient")
    private final WebClient tinkoffPulseWebClient;

    public String getStockIdeas(String url) {
        return tinkoffPulseWebClient.get()
                .uri(pulseStockIdeasUrl + url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getStockNews() {
        return tinkoffPulseWebClient.get()
                .uri(pulseStocksNewsUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getBrandsInfo() {
        return tinkoffPulseWebClient.get()
                .uri(pulseBrandsInfoUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
