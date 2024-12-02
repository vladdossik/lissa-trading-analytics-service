package lissa.trading.analytics.service.client.pulse;

import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.FullBrandInfoDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.FullIdeaDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.FullNewsDto;
import lissa.trading.analytics.service.exception.TinkoffPulseClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.View;

@Service
@RequiredArgsConstructor
@Slf4j
@Component
public class TinkoffPulseClient {

    private final View error;
    @Value("${integration.pulse.stock-news-url}")
    private String pulseStocksNewsUrl;
    @Value("${integration.pulse.brands-info-url}")
    private String pulseBrandsInfoUrl;
    @Value("${integration.pulse.stock-ideas-url}")
    private String pulseStockIdeasUrl;

    private final WebClient tinkoffPulseWebClient;

    public FullIdeaDto getStockIdeas(String url) {
        FullIdeaDto fullIdeaDto = tinkoffPulseWebClient.get()
                .uri(pulseStockIdeasUrl + url)
                .retrieve()
                .bodyToMono(FullIdeaDto.class)
                .doOnError(error -> {
                    log.error("Failed to retrieve stock ideas", error);
                    throw new TinkoffPulseClientException("Failed to retrieve stock ideas");
                })
                .block();

        if (fullIdeaDto == null) {
            log.error("Fetched null from: {}", url);
            throw new TinkoffPulseClientException("Fetched null from: " + url);
        }
        return fullIdeaDto;
    }

    public FullNewsDto getStockNews() {
        FullNewsDto fullNewsDto = tinkoffPulseWebClient.get()
                .uri(pulseStocksNewsUrl)
                .retrieve()
                .bodyToMono(FullNewsDto.class)
                .doOnError(error -> {
                    log.error("Failed to retrieve stock news", error);
                    throw new TinkoffPulseClientException("Failed to retrieve stock news");
                })
                .block();

        if (fullNewsDto == null) {
            log.error("Fetched null from: {}", pulseStocksNewsUrl);
            throw new TinkoffPulseClientException("Fetched null from: " + pulseStockIdeasUrl);
        }
        return fullNewsDto;
    }

    public FullBrandInfoDto getBrandsInfo() {
        FullBrandInfoDto fullBrandInfoDto = tinkoffPulseWebClient.get()
                .uri(pulseBrandsInfoUrl)
                .retrieve()
                .bodyToMono(FullBrandInfoDto.class)
                .doOnError(error -> {
                    log.error("Failed to retrieve brands info", error);
                    throw new TinkoffPulseClientException("Failed to retrieve brands info");
                })
                .block();

        if (fullBrandInfoDto == null) {
            log.error("Fetched null from: {}", pulseBrandsInfoUrl);
            throw new TinkoffPulseClientException("Fetched null from: " + pulseBrandsInfoUrl);
        }
        return fullBrandInfoDto;
    }
}
