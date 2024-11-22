package lissa.trading.analytics.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotEmpty;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandInfoResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.ideas.StockIdeasResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.StockNewsResponseDto;
import lissa.trading.analytics.service.service.tinkoffPulse.TinkoffPulseBrandInfoService;
import lissa.trading.analytics.service.service.tinkoffPulse.TinkoffPulseIdeasService;
import lissa.trading.analytics.service.service.tinkoffPulse.TinkoffPulseNewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/analytics/tinkoff-pulse")
@Slf4j
public class TinkoffPulseController {

    private final TinkoffPulseNewsService tinkoffPulseNewsService;
    private final TinkoffPulseIdeasService tinkoffPulseIdeasService;
    private final TinkoffPulseBrandInfoService tinkoffPulseBrandInfoService;

    @Operation(summary = "Получить новости по тикерам из Tinkoff Pulse",
            description = "Возвращает новости по запрошенным тикерам")
    @ApiResponse(responseCode = "200", description = "Новости успешно получены")
    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    @GetMapping("news")
    List<StockNewsResponseDto> getTinkoffPulseNews(@RequestParam @NotEmpty List<String> tickers) {
        log.info("Requesting news from Tinkoff Pulse with params: {}", tickers);
        return tinkoffPulseNewsService.getData(tickers);
    }

    @Operation(summary = "Получить идеи по тикерам из Tinkoff Pulse",
            description = "Возвращает идеи по запрошенным тикерам")
    @ApiResponse(responseCode = "200", description = "Идеи успешно получены")
    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    @GetMapping("ideas")
    List<StockIdeasResponseDto> getTinkoffPulseIdeas(@RequestParam @NotEmpty List<String> tickers) {
        log.info("Requesting ideas from Tinkoff Pulse with params: {}", tickers);
        return tinkoffPulseIdeasService.getData(tickers);
    }

    @Operation(summary = "Получить информации о компаниях по тикерам из Tinkoff Pulse",
            description = "Возвращает информацию о компаниях по запрошенным тикерам")
    @ApiResponse(responseCode = "200", description = "Информация успешно получена")
    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    @GetMapping("brand-info")
    List<BrandInfoResponseDto> getTinkoffPulseBrandInfo(@RequestParam @NotEmpty List<String> tickers) {
        log.info("Requesting brand info from Tinkoff Pulse with params: {}", tickers);
        return tinkoffPulseBrandInfoService.getData(tickers);
    }
}
