package lissa.trading.analytics.service.service.tinkoffPulse;

import java.util.List;

public interface TinkoffPulseService<T> {
    T getData(List<String> tickers);
}
