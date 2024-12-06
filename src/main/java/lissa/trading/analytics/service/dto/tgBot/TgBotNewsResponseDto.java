package lissa.trading.analytics.service.dto.tgBot;

import lissa.trading.analytics.service.dto.NewsSourceResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TgBotNewsResponseDto {
    private InfoType type;
    private Long chatId;
    private List<NewsSourceResponseDto> data;
}