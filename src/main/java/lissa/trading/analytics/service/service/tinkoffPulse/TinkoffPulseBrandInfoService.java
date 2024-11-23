package lissa.trading.analytics.service.service.tinkoffPulse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lissa.trading.analytics.service.client.tinkoff.pulse.TinkoffPulseClient;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandInfoDto;
import lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo.BrandInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("brandInfoService")
@RequiredArgsConstructor
@Slf4j
public class TinkoffPulseBrandInfoService implements TinkoffPulseService<List<BrandInfoResponseDto>> {

    private final TinkoffPulseClient tinkoffPulseClient;

    @Override
    public List<BrandInfoResponseDto> getData(List<String> tickers) {
        log.info("Getting brands info data from Tinkoff Pulse");
        List<BrandInfoResponseDto> responseDtos = new ArrayList<>();
        for (String ticker : tickers) {
            BrandInfoDto brandInfoDto = processBrandsByTicker().get(ticker);
            if (brandInfoDto != null) {
                responseDtos.add(BrandInfoResponseDto.builder()
                        .message("Информация о компании по тикеру: " + ticker)
                        .brandInfo(brandInfoDto)
                        .build());
            } else {
                responseDtos.add(BrandInfoResponseDto.builder()
                        .message("Не удалось найти информацию о компании по тикеру: " + ticker)
                        .brandInfo(new BrandInfoDto())
                        .build());
            }
        }
        return responseDtos;
    }

    private Map<String, BrandInfoDto> processBrandsByTicker() {
        Map<String, BrandInfoDto> brandsByTicker = new HashMap<>();

        for (BrandInfoDto brand : getAllBrandInfo()) {
            for (String ticker : brand.getTickers()) {
                brandsByTicker.put(ticker, brand);
            }
        }

        return brandsByTicker;
    }

    private List<BrandInfoDto> getAllBrandInfo() {
        try {
            String json = tinkoffPulseClient.getBrandsInfo();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(
                    mapper.readTree(json)
                            .path("payload")
                            .path("brands")
                            .toString(),
                    new TypeReference<>() {
                    });
        } catch (Exception e) {
            log.error("Error parsing news", e);
            throw new RuntimeException(e);
        }
    }
}
