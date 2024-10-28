package lissa.trading.analytics.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndicatorsDto {
    private Double indicatorSMA;
    private Double indicatorEMA;
    private Double indicatorRSI;
}