package lissa.trading.analytics.service.dto.TinkoffPulse.news;

import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockNewsResponseDto extends ResponseDto {
    private List<StockNewsDto> items;
}
