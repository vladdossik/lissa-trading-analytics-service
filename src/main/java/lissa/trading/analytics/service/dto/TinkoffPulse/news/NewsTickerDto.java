package lissa.trading.analytics.service.dto.TinkoffPulse.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsTickerDto {
    private String type;
    private String ticker;
    private String currency;
    private Double price;
}
