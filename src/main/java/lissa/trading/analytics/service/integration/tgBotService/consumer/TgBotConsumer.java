package lissa.trading.analytics.service.integration.tgBotService.consumer;

import lissa.trading.analytics.service.dto.NewsSourceResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lissa.trading.analytics.service.dto.tgBot.InfoType;
import lissa.trading.analytics.service.dto.tgBot.TgBotNewsResponseDto;
import lissa.trading.analytics.service.dto.tgBot.TgBotPulseResponseDto;
import lissa.trading.analytics.service.dto.tgBot.TgBotRequestDto;
import lissa.trading.analytics.service.integration.tgBotService.publisher.TgBotProducer;
import lissa.trading.analytics.service.service.news.MainNewsService;
import lissa.trading.analytics.service.service.tinkoffPulse.TinkoffPulseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TgBotConsumer {

    @Qualifier("newsService")
    private final TinkoffPulseService pulseNewsService;

    @Qualifier("ideasService")
    private final TinkoffPulseService pulseIdeasService;

    @Qualifier("brandInfoService")
    private final TinkoffPulseService pulseBrandInfoService;

    private final MainNewsService newsService;

    private final TgBotProducer tgBotProducer;

    @RabbitListener(queues = "${integration.rabbit.tg-bot-service.request-queue}")
    public void handleRequest(TgBotRequestDto request) {
        log.info("Receives message {}", request);
        if (request != null) {
            InfoType type = request.getType();

            switch (type) {
                case NEWS -> handleNewsRequest(request);
                case PULSE_NEWS, IDEAS, BRAND_INFO -> handlePulseRequest(request);
            }

        } else {
            log.error("Error getting news request from TgBot");
            throw new RuntimeException("Error getting request from TgBot");
        }
    }

    private void handleNewsRequest(TgBotRequestDto requestDto) {
        List<String> tickers = requestDto.getTickers();

        List<NewsSourceResponseDto> news = newsService.getNews(tickers);
        TgBotNewsResponseDto newsResponseDto = new TgBotNewsResponseDto(InfoType.NEWS, requestDto.getChatId(), news);
        tgBotProducer.sendNewsData(newsResponseDto);
    }

    private void handlePulseRequest(TgBotRequestDto requestDto) {
        List<String> tickers = requestDto.getTickers();
        List<ResponseDto> data = new ArrayList<>();

        switch (requestDto.getType()) {
            case PULSE_NEWS -> data = pulseNewsService.getData(tickers);
            case IDEAS -> data = pulseIdeasService.getData(tickers);
            case BRAND_INFO -> data = pulseBrandInfoService.getData(tickers);
        }

        TgBotPulseResponseDto pulseResponseDto = new TgBotPulseResponseDto(requestDto.getType(),
                requestDto.getChatId(), data);
        tgBotProducer.sendPulseData(pulseResponseDto);
    }

}
