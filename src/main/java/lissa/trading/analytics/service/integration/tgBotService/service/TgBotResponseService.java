package lissa.trading.analytics.service.integration.tgBotService.service;

import lissa.trading.analytics.service.dto.tgBot.TgBotNewsResponseDto;
import lissa.trading.analytics.service.dto.tgBot.TgBotPulseResponseDto;
import lissa.trading.analytics.service.integration.ResponseService;
import lissa.trading.analytics.service.integration.tgBotService.TgBotSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TgBotResponseService implements ResponseService {

    private final TgBotSender tgBotSender;

    @Override
    public void sendPulseResponse(TgBotPulseResponseDto responseDto) {
        tgBotSender.sendPulseData(responseDto);
    }

    @Override
    public void sendNewsResponse(TgBotNewsResponseDto responseDto) {
        tgBotSender.sendNewsData(responseDto);
    }
}
