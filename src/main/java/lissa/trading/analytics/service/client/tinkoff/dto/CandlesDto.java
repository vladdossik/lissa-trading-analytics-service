package lissa.trading.analytics.service.client.tinkoff.dto;

import lissa.trading.analytics.service.dto.CandleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandlesDto {
    private List<CandleDto> candles;
}
