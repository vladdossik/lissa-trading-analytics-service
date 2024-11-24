package lissa.trading.analytics.service.service.tinkoffPulse;

import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;

import java.util.List;

public interface TinkoffPulseService {
    List<? extends ResponseDto> getData(List<String> tickers);
}
