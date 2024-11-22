package lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class BrandInfoDto {
    List<String> tickers;
    BrandLinkDto externalLinks;
    private String name;
    private String brandInfo;
    private String sector;
    private String country;
}
