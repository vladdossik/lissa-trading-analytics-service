package lissa.trading.analytics.service.service.news;

import lissa.trading.analytics.service.dto.NewsResponseDto;
import lissa.trading.analytics.service.exception.UnsupportedSourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainNewsService implements NewsService {

    private final FinamNewsService finamNewsService;

    @Override
    public NewsResponseDto getNews(String source, List<String> tickers) {
        switch (source.toLowerCase()) {
            case "finam":
                return finamNewsService.getNews(tickers);
            // реализации будут дополняться
            default:
                log.error("Unsupported source: {}", source);
                throw new UnsupportedSourceException("Unsupported source: " + source);
        }
    }
}
