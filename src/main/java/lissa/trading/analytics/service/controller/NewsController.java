package lissa.trading.analytics.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.dto.NewsResponseDto;
import lissa.trading.analytics.service.service.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/analytics/news")
public class NewsController {
    private final NewsService newsService;

    @Operation(summary = "Получить новости по компаниям",
            description = "Возвращает финансовые новости по запрошенным компаниям")
    @ApiResponse(
            responseCode = "200",
            description = "Новости успешно получены",
            content = @Content(schema = @Schema(implementation = IndicatorsDto.class))
    )
    @GetMapping
    NewsResponseDto getNews(@RequestParam List<String> keywords){
        return newsService.getNews(keywords);
    }
}
