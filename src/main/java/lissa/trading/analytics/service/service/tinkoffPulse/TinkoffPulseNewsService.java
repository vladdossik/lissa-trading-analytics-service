package lissa.trading.analytics.service.service.tinkoffPulse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lissa.trading.analytics.service.client.gpt.TinkoffPulseClient;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.NewsTickerDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.StockNewsDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.StockNewsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service("newsService")
public class TinkoffPulseNewsService implements TinkoffPulseService<List<StockNewsResponseDto>> {

    private final TinkoffPulseClient tinkoffPulseClient;

    @Override
    public List<StockNewsResponseDto> getData(List<String> tickers) {
        log.info("Getting news data from Tinkoff Pulse");
        List<StockNewsDto> newsDtoList = getAllNews();
        Map<String, List<StockNewsDto>> newsMap = processNewsByTickers(newsDtoList);

        List<StockNewsResponseDto> responseDtoList = new ArrayList<>();

        for (String ticker : tickers) {
            if (!newsMap.containsKey(ticker)) {
                responseDtoList.add(StockNewsResponseDto.builder()
                        .message("Не найдено новостей по тикеру: " + ticker)
                        .items(Collections.emptyList())
                        .build());
            } else {
                List<StockNewsDto> news = newsMap.get(ticker);
                responseDtoList.add(StockNewsResponseDto.builder()
                        .message("Найдено " + news.size() + " новостей по тикеру: " + ticker)
                        .items(news)
                        .build());
            }
        }
        return responseDtoList;
    }

    private Map<String, List<StockNewsDto>> processNewsByTickers(List<StockNewsDto> allNews) {
        Map<String, List<StockNewsDto>> newsMap = new HashMap<>();
        for (StockNewsDto news : allNews) {
            List<NewsTickerDto> tickers = news.getContent().getInstruments();
            news.setUrl("https://www.tbank.ru/invest/social/profile/"
                    + news.getOwner().getNickname() + "/" + news.getId());
            for (NewsTickerDto ticker : tickers) {
                newsMap.computeIfAbsent(ticker.getTicker(), k -> new ArrayList<>()).add(news);
            }
        }
        return newsMap;
    }

    private List<StockNewsDto> getAllNews() {
        try {
            String json = tinkoffPulseClient.getStockNews();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(
                    mapper.readTree(json)
                            .path("payload")
                            .path("items")
                            .toString(),
                    new TypeReference<>() {
                    });
        } catch (Exception e) {
            log.error("Error parsing news", e);
            throw new RuntimeException(e);
        }
    }
}
