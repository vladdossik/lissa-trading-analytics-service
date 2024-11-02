package lissa.trading.analytics.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Технические индикаторы")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndicatorsDto {
    private Double indicatorSMA;
    private Double indicatorEMA;
    private Double indicatorRSI;
}