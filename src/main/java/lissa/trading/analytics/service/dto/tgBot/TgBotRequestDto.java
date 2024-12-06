package lissa.trading.analytics.service.dto.tgBot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TgBotRequestDto {
    private InfoType type;
    private Long chatId;
    private List<String> tickers;
}