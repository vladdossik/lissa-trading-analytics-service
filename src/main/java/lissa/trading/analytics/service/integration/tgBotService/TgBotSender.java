package lissa.trading.analytics.service.integration.tgBotService;

import lissa.trading.analytics.service.dto.tgBot.TgBotNewsResponseDto;
import lissa.trading.analytics.service.dto.tgBot.TgBotPulseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TgBotSender {

    @Value("${integration.rabbit.exchanges.analytics}")
    private String analyticsExchange;

    @Value("${integration.rabbit.routing-keys.tg-bot.response.pulse}")
    private String pulseResponseRoutingKey;

    @Value("${integration.rabbit.routing-keys.tg-bot.response.news}")
    private String newsResponseRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendPulseData(TgBotPulseResponseDto responseDto) {
        rabbitTemplate.convertAndSend(analyticsExchange, pulseResponseRoutingKey, responseDto);
    }

    public void sendNewsData(TgBotNewsResponseDto newsResponseDto) {
        rabbitTemplate.convertAndSend(analyticsExchange, newsResponseRoutingKey, newsResponseDto);
    }
}
