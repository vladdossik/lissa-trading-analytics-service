package lissa.trading.analytics.service.tinkoff.dto;

import lissa.trading.analytics.service.model.CandleInterval;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TinkoffCandlesRequestDto {
    private String instrumentId;
    private OffsetDateTime from;
    private OffsetDateTime to;
    private CandleInterval interval;
}
