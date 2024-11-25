package lissa.trading.analytics.service.dto.TinkoffPulse.news;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsContentDto {
    private String type;
    private List<NewsTickerDto> instruments;

    @JsonAlias({"text", "title"})
    private String text;
}
