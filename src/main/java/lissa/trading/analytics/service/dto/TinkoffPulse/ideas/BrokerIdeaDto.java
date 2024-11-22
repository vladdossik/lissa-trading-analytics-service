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
public class BrokerIdeaDto {
    private String id;
    private String name;
    private Double accuracy;
}
