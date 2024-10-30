package lissa.trading.analytics.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandleDto {
    private long volume;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private OffsetDateTime time;
    private boolean isComplete;
}
