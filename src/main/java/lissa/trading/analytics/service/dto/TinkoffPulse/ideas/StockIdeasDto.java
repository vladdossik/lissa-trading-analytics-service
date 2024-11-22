package lissa.trading.analytics.service.dto.TinkoffPulse.ideas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockIdeasDto {
    private String id;
    private String title;
    private BrokerIdeaDto broker;
    private List<TickerIdeaDto> tickers;
    private Double yield;
    private String url;

    @JsonProperty("date_start")
    private String dateStart;

    @JsonProperty("date_end")
    private String dateEnd;

    @JsonProperty("price_start")
    private Double priceStart;

    @JsonProperty("price")
    private Double actualPrice;

    @JsonProperty("target_yield")
    private Double targetYield;
}
