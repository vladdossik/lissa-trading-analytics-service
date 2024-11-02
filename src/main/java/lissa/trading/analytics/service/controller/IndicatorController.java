package lissa.trading.analytics.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffCandlesRequestDto;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.service.indicator.IndicatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/analytics/indicators")
@RequiredArgsConstructor
public class IndicatorController {
    private final IndicatorService indicatorService;

    @Operation(summary = "Получить технические индикаторы",
            description = "Возвращает технические индикаторы на основе исторических данных о свечах")
    @ApiResponse(responseCode = "200", description = "Индикаторы успешно рассчитаны",
            content = @Content(schema = @Schema(implementation = IndicatorsDto.class)))
    @PostMapping
    public IndicatorsDto getIndicators(@RequestBody TinkoffCandlesRequestDto candlesRequestDto) {
        log.info("Requesting for tech. indicators with params: {}", candlesRequestDto);
        return indicatorService.getIndicators(candlesRequestDto);
    }
}
