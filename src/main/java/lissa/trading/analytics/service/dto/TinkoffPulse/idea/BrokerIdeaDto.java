package lissa.trading.analytics.service.dto.TinkoffPulse.idea;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrokerIdeaDto {
    private String id;
    private String name;
    private Double accuracy;
}
