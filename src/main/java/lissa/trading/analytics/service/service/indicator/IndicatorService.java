package lissa.trading.analytics.service.service.indicator;

import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffCandlesRequestDto;
import lissa.trading.analytics.service.dto.IndicatorsDto;

public interface IndicatorService {
    IndicatorsDto getIndicators(TinkoffCandlesRequestDto candlesRequestDto);
}
