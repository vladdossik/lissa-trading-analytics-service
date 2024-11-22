package lissa.trading.analytics.service.dto.TinkoffPulse.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lissa.trading.analytics.service.mapper.NewsContentDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize(using = NewsContentDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsContentDto {
    private String type;
    private String text;
    private List<NewsTickerDto> instruments;
}
