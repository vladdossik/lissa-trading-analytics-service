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

    @Value("${integration.rabbit.inbound.tg-bot.exchange}")
    private String analyticsExchange;

    @Value("${integration.rabbit.outbound.tg-bot.pulse.routing-key}")
    private String pulseResponseRoutingKey;

    @Value("${integration.rabbit.outbound.tg-bot.news.routing-key}")
    private String newsResponseRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendPulseData(TgBotPulseResponseDto responseDto) {
        rabbitTemplate.convertAndSend(analyticsExchange, pulseResponseRoutingKey, responseDto);
    }

    public void sendNewsData(TgBotNewsResponseDto newsResponseDto) {
        rabbitTemplate.convertAndSend(analyticsExchange, newsResponseRoutingKey, newsResponseDto);
    }
}
