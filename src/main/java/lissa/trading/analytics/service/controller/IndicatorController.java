package lissa.trading.analytics.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.service.indicator.IndicatorService;
import lissa.trading.analytics.service.tinkoff.dto.TinkoffCandlesRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/analytics/indicators")
@Tag(name = "Indicator", description = "Позволяет получить технические индикаторы для инструментов")
@RequiredArgsConstructor
public class IndicatorController {
    private final IndicatorService indicatorService;

    @PostMapping
    @Operation(summary = "Получить технические индикаторы для инструмента",
            description = "Позволяет получить технические индикаторы для инструментов")
    public IndicatorsDto getIndicators(@RequestBody TinkoffCandlesRequestDto candlesRequestDto) {
        log.info("Requesting for tech. indicators with params: {}", candlesRequestDto);
        return indicatorService.getIndicators(candlesRequestDto);
    }
}
