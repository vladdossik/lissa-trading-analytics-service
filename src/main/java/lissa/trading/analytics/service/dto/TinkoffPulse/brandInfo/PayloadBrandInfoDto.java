package lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadBrandInfoDto {
    private List<BrandInfoDto> brands;
}
