package lissa.trading.analytics.service.mapper;

import lissa.trading.analytics.service.dto.TinkoffPulse.idea.StockIdeaFromPulseDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.idea.StockIdeaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockIdeaMapper {

    StockIdeaDto toStockIdeasForOtherServices(StockIdeaFromPulseDto stockIdeaFromPulseDto);
}
