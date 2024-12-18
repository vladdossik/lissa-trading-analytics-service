package lissa.trading.analytics.service.client.tinkoff.feign;

import lissa.trading.analytics.service.client.tinkoff.dto.CandlesDto;
import lissa.trading.analytics.service.client.tinkoff.dto.CompanyNamesDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffCandlesRequestDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "StockServiceClient", url = "${integration.rest.tinkoff-api-service-url}/v1/internal")
public interface StockServiceClient {

    @PostMapping("/set-token")
    void setTinkoffToken(@RequestBody TinkoffTokenDto tinkoffToken);

    @PostMapping("/candles")
    CandlesDto getCandles(@RequestBody TinkoffCandlesRequestDto candlesRequestDto);

    @GetMapping("/companies")
    CompanyNamesDto getCompanyNamesByTickers(@RequestParam List<String> tickers);

}
