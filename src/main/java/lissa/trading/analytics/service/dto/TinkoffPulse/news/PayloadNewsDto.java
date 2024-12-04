package lissa.trading.analytics.service.dto.TinkoffPulse.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadNewsDto {
    private List<StockNewsDto> items;
}
