package lissa.trading.analytics.service.integration;

import lissa.trading.analytics.service.dto.tgBot.TgBotRequestDto;

public interface RequestService {
    void processRequest(TgBotRequestDto requestDto);
}
