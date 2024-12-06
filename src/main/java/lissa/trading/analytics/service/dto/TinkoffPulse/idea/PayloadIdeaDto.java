package lissa.trading.analytics.service.dto.TinkoffPulse.idea;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadIdeaDto {
    private List<StockIdeaFromPulseDto> ideas;
}
