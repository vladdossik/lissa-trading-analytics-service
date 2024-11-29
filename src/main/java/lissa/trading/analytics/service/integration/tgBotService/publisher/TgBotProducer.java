package lissa.trading.analytics.service.integration.tgBotService.publisher;

import lissa.trading.analytics.service.dto.tgBot.TgBotNewsResponseDto;
import lissa.trading.analytics.service.dto.tgBot.TgBotPulseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TgBotProducer {

    @Value("${integration.rabbit.tg-bot-service.pulse-response-queue}")
    private String pulseResponseQueue;

    @Value("${integration.rabbit.tg-bot-service.news-response-queue}")
    private String newsResponseQueue;

    private final RabbitTemplate rabbitTemplate;

    public void sendPulseData(TgBotPulseResponseDto responseDto) {
        rabbitTemplate.convertAndSend(pulseResponseQueue, responseDto);
    }

    public void sendNewsData(TgBotNewsResponseDto newsResponseDto) {
        rabbitTemplate.convertAndSend(newsResponseQueue, newsResponseDto);
    }
}
