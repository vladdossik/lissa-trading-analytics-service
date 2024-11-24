package lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayloadBrandInfoDto {
    private List<BrandInfoDto> brands;
}
