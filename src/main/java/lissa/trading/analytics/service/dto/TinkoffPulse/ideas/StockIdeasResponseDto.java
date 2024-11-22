package lissa.trading.analytics.service.dto.TinkoffPulse.ideas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockIdeasResponseDto {
    private String message;
    private List<StockIdeasDto> ideas;
}
