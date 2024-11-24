package lissa.trading.analytics.service.dto.TinkoffPulse.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class StockNewsResponseDto implements ResponseDto {
    private String message;
    private List<StockNewsDto> items;
}
