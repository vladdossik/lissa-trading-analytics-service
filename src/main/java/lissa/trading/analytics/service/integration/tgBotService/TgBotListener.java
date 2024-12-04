package lissa.trading.analytics.service.integration.tgBotService;

import lissa.trading.analytics.service.dto.tgBot.TgBotRequestDto;
import lissa.trading.analytics.service.integration.tgBotService.service.TgBotRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TgBotListener {

    private final TgBotRequestService requestService;

    @RabbitListener(queues = "${integration.rabbit.queues.inbound.tg-bot.request}")
    public void handleRequest(TgBotRequestDto request) {
        log.info("Received message {}", request);
        if (request != null) {
            requestService.processRequest(request);
        } else {
            log.error("Error getting news request from TgBot");
            throw new RuntimeException("Error getting request from TgBot");
        }
    }
}