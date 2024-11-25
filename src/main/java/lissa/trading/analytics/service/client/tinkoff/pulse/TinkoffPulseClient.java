package lissa.trading.analytics.service.client.tinkoff.pulse;

import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.FullBrandInfoDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.FullIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.FullNewsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
@Component
public class TinkoffPulseClient {

    @Value("${integration.pulse.stock-news-url}")
    private String pulseStocksNewsUrl;
    @Value("${integration.pulse.brands-info-url}")
    private String pulseBrandsInfoUrl;
    @Value("${integration.pulse.stock-ideas-url}")
    private String pulseStockIdeasUrl;

    private final WebClient tinkoffPulseWebClient;

    public FullIdeaDto getStockIdeas(String url) {
        return tinkoffPulseWebClient.get()
                .uri(pulseStockIdeasUrl + url)
                .retrieve()
                .bodyToMono(FullIdeaDto.class)
                .block();
    }

    public FullNewsDto getStockNews() {
        return tinkoffPulseWebClient.get()
                .uri(pulseStocksNewsUrl)
                .retrieve()
                .bodyToMono(FullNewsDto.class)
                .block();
    }

    public FullBrandInfoDto getBrandsInfo() {
        return tinkoffPulseWebClient.get()
                .uri(pulseBrandsInfoUrl)
                .retrieve()
                .bodyToMono(FullBrandInfoDto.class)
                .block();
    }
}
