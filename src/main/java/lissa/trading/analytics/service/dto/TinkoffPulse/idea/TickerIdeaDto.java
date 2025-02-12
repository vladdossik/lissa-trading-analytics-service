package lissa.trading.analytics.service.dto.TinkoffPulse.idea;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TickerIdeaDto {
    private String ticker;
    private String name;
    private Double price;
}
