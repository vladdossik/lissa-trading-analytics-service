package lissa.trading.analytics.service.client.gpt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
@Component
public class TinkoffPulseClient {

    private final WebClient webClient = WebClient.create();
    @Value("${security.tinkoff.pulse.stock-news-url}")
    private String pulseStocksNewsUrl;
    @Value("${security.tinkoff.pulse.brands-info-url}")
    private String pulseBrandsInfoUrl;
    @Value("${security.tinkoff.pulse.stock-ideas-url}")
    private String pulseStockIdeasUrl;

    public String getStockIdeas(String url) {
        return webClient.get()
                .uri(pulseStockIdeasUrl + url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getStockNews() {
        return webClient.get()
                .uri(pulseStocksNewsUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public Flux<DataBuffer> getBrandsInfo() {
        return webClient.get()
                .uri(pulseBrandsInfoUrl)
                .retrieve()
                .bodyToFlux(DataBuffer.class);
    }
}
