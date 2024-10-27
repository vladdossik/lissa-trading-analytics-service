package lissa.trading.analytics.service.tinkoff.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lissa.trading.analytics.service.model.CandleInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Schema(description = "Тело запроса для получения анализа технических индикаторов")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TinkoffCandlesRequestDto {

    @Schema(description = "Уникальный идентификатор инструмента в T-Invest. Может использоваться figi (считается " +
            "deprecated)")
    private String instrumentId;
    private OffsetDateTime from;
    private OffsetDateTime to;
    @Schema(description = "Интервал по которому нужна информация о движении свечи", example = "CANDLE_INTERVAL_5_MIN")
    private CandleInterval interval;
}
