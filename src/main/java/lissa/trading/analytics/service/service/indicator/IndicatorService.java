package lissa.trading.analytics.service.service.indicator;

import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.tinkoff.dto.TinkoffCandlesRequestDto;

public interface IndicatorService {
    IndicatorsDto getIndicators(TinkoffCandlesRequestDto candlesRequestDto);
}
