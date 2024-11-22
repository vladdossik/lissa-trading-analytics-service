package lissa.trading.analytics.service.dto.TinkoffPulse.ideas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerIdeaDto {
    private String ticker;
    private String name;
    private Double price;
}
