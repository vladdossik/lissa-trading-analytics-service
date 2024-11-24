package lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo;

import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandInfoResponseDto implements ResponseDto {
    private String message;
    private BrandInfoDto brandInfo;
}
