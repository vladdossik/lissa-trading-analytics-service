package lissa.trading.analytics.service.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.NewsContentDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.news.NewsTickerDto;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NewsContentDeserializer extends StdDeserializer<NewsContentDto> {

    public NewsContentDeserializer() {
        super(NewsContentDto.class);
    }

    @Override
    public NewsContentDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        String type = node.get("type").asText();
        String text = node.has("text") ? node.get("text").asText()
                : node.has("title") ? node.get("title").asText() : null;

        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        NewsContentDto dto = new NewsContentDto();

        dto.setType(type);
        dto.setText(text);

        if (node.has("instruments")) {
            List<NewsTickerDto> instruments = Arrays.asList(
                    mapper.treeToValue(node.get("instruments"), NewsTickerDto[].class)
            );
            dto.setInstruments(instruments);
        }

        return dto;
    }
}
