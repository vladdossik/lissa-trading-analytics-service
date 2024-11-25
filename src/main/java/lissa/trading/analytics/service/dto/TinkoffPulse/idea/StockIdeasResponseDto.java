package lissa.trading.analytics.service.dto.TinkoffPulse.idea;

import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockIdeasResponseDto extends ResponseDto {
    private List<StockIdeaDto> ideas;
}
