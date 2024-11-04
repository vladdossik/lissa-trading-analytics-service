package lissa.trading.analytics.service.service.indicator;

import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffCandlesRequestDto;

public interface IndicatorService {
    IndicatorsDto getIndicators(TinkoffCandlesRequestDto candlesRequestDto);
}
