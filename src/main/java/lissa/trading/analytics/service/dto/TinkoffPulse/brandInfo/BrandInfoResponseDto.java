package lissa.trading.analytics.service.dto.TinkoffPulse.brandInfo;

import lissa.trading.analytics.service.dto.TinkoffPulse.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandInfoResponseDto extends ResponseDto {
    private BrandInfoDto brandInfo;
}
