package lissa.trading.analytics.service.tinkoff.dto;

import lissa.trading.analytics.service.model.Candle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandlesDto {
    List<Candle> candles;
}
