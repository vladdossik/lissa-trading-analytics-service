package lissa.trading.analytics.service.tinkoff.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lissa.trading.analytics.service.dto.CandleInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Schema(description = "Данные необходимые для запроса свечей в тинькофф сервисе")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TinkoffCandlesRequestDto {
    @Schema(description = "UUID или figi инструмента в API Тинькофф")
    private String instrumentId;
    private OffsetDateTime from;
    private OffsetDateTime to;
    @Schema(description = "Интервал между свечами", example = "CANDLE_INTERVAL_5_MIN")
    private CandleInterval interval;
}
