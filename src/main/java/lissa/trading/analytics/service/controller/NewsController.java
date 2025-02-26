package lissa.trading.analytics.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotEmpty;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.dto.NewsSourceResponseDto;
import lissa.trading.analytics.service.service.news.MainNewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/v1/analytics/news")
public class NewsController {

    private final MainNewsService newsService;

    @Operation(summary = "Получить новости по компаниям",
            description = "Возвращает финансовые новости по запрошенным компаниям")
    @ApiResponse(responseCode = "200", description = "Новости успешно получены",
            content = @Content(schema = @Schema(implementation = NewsSourceResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    @GetMapping
    List<NewsSourceResponseDto> getNews(@RequestParam @NotEmpty List<String> tickers) {
        log.info("Requesting getNews endpoint with params: {}", tickers);
        return newsService.getNews(tickers);
    }
}
