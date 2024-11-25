package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.dto.NewsResponseDto;
import lissa.trading.analytics.service.dto.NewsSourceResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainNewsService {

    private final List<NewsService> newsServices;

    public List<NewsSourceResponseDto> getNews(List<String> tickers) {
        List<NewsSourceResponseDto> newsSourceResponseDtos = new ArrayList<>();
        for (NewsService service : newsServices) {
            NewsResponseDto response = service.getNews(tickers);
            if (response != null) {
                newsSourceResponseDtos.add(new NewsSourceResponseDto(service.getSourceName(), response));
            } else {
                log.error("Error getting news for {}", tickers);
                throw new RuntimeException();
            }
        }
        return newsSourceResponseDtos;
    }
}
