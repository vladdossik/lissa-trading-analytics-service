package lissa.trading.analytics.service.config;

import lissa.trading.analytics.service.service.news.FinamNewsService;
import lissa.trading.analytics.service.service.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class NewsServiceListConfig {

    private final FinamNewsService finamNewsService;

    @Bean
    public List<NewsService> newsServiceList() {
        return List.of(finamNewsService);
//      будет дополняться
    }
}
