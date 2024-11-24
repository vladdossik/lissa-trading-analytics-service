package lissa.trading.analytics.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotEmpty;
import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandInfoResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.StockIdeasResponseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.StockNewsResponseDto;
import lissa.trading.analytics.service.service.tinkoffPulse.TinkoffPulseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("newsService")
    private final TinkoffPulseService tinkoffPulseNewsService;

    @Qualifier("ideasService")
    private final TinkoffPulseService tinkoffPulseIdeasService;

    @Qualifier("brandInfoService")
    private final TinkoffPulseService tinkoffPulseBrandInfoService;

    @Operation(summary = "Получить новости по тикерам из Tinkoff Pulse",
            description = "Возвращает новости по запрошенным тикерам")
    @ApiResponse(responseCode = "200", description = "Новости успешно получены",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = StockNewsResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    @GetMapping("news")
    List<? extends ResponseDto> getTinkoffPulseNews(@RequestParam @NotEmpty List<String> tickers) {
        log.info("Requesting news from Tinkoff Pulse with params: {}", tickers);
        return tinkoffPulseNewsService.getData(tickers);
    }

    @Operation(summary = "Получить идеи по тикерам из Tinkoff Pulse",
            description = "Возвращает идеи по запрошенным тикерам")
    @ApiResponse(responseCode = "200", description = "Идеи успешно получены",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = StockIdeasResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    @GetMapping("ideas")
    List<? extends ResponseDto> getTinkoffPulseIdeas(@RequestParam @NotEmpty List<String> tickers) {
        log.info("Requesting ideas from Tinkoff Pulse with params: {}", tickers);
        return tinkoffPulseIdeasService.getData(tickers);
    }

    @Operation(summary = "Получить информацию о компаниях по тикерам из Tinkoff Pulse",
            description = "Возвращает информацию о компаниях по запрошенным тикерам")
    @ApiResponse(responseCode = "200", description = "Информация успешно получена",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BrandInfoResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    @GetMapping("brand-info")
    List<? extends ResponseDto> getTinkoffPulseBrandInfo(@RequestParam @NotEmpty List<String> tickers) {
        log.info("Requesting brand info from Tinkoff Pulse with params: {}", tickers);
        return tinkoffPulseBrandInfoService.getData(tickers);
    }
}
