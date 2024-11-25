package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.dto.NewsResponseDto;

import java.util.List;

public interface NewsService {
    NewsResponseDto getNews(List<String> tickers);
    String getSourceName();
}
