package lissa.trading.analytics.service.integration;

import lissa.trading.analytics.service.dto.tgBot.TgBotNewsResponseDto;
import lissa.trading.analytics.service.dto.tgBot.TgBotPulseResponseDto;

public interface ResponseService {
    void sendPulseResponse(TgBotPulseResponseDto responseDto);

    void sendNewsResponse(TgBotNewsResponseDto responseDto);
}
