package lissa.trading.analytics.service.dto.TinkoffPulse.idea;

import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockIdeaDto extends ResponseDto {
    private String id;
    private String title;
    private BrokerIdeaDto broker;
    private List<TickerIdeaDto> tickers;
    private Double yield;
    private String url;
    private String dateStart;
    private String dateEnd;
    private Double priceStart;
    private Double actualPrice;
    private Double targetYield;
}
