package lissa.trading.analytics.service.dto.TinkoffPulse.idea;

import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockIdeasResponseDto implements ResponseDto {
    private String message;
    private List<StockIdeaDto> ideas;
}
