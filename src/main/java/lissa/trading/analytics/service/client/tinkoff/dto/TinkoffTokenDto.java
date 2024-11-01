package lissa.trading.analytics.service.client.tinkoff.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Токен для доступа к внутреннему Тинькофф Сервису")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TinkoffTokenDto {
    private String token;
}
