package lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandInfoResponseDto {
    private String message;
    private BrandInfoDto brandInfo;
}
