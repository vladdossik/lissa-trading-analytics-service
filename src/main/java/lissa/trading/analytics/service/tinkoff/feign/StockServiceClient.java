package lissa.trading.analytics.service.tinkoff.feign;

import lissa.trading.analytics.service.tinkoff.dto.CandlesDto;
import lissa.trading.analytics.service.tinkoff.dto.TinkoffCandlesRequestDto;
import lissa.trading.analytics.service.tinkoff.dto.TinkoffTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "StockServiceClient", url = "${integration.rest.tinkoff-api-service-url}/v1/internal")
public interface StockServiceClient {

    @PostMapping("/candles")
    CandlesDto getCandles(@RequestBody TinkoffCandlesRequestDto candlesRequestDto);

    @PostMapping("/set-token")
    void setTinkoffToken(@RequestBody TinkoffTokenDto tinkoffToken);
}
