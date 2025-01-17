package lissa.trading.analytics.service.service.indicator;

import lissa.trading.analytics.service.client.tinkoff.dto.CandlesDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffCandlesRequestDto;
import lissa.trading.analytics.service.client.tinkoff.dto.TinkoffTokenDto;
import lissa.trading.analytics.service.client.tinkoff.feign.StockServiceClient;
import lissa.trading.analytics.service.dto.IndicatorsDto;
import lissa.trading.analytics.service.exception.CandlesNotFoundException;
import lissa.trading.lissa.auth.lib.dto.UserInfoDto;
import lissa.trading.lissa.auth.lib.security.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndicatorServiceImpl implements IndicatorService {

    private final StockServiceClient stockServiceClient;
    private final IndicatorCalculator indicatorCalculator;

    @Override
    public IndicatorsDto getIndicators(TinkoffCandlesRequestDto candlesRequestDto) {
        setTinkoffApiToken();
        log.info("Requesting tinkoff-api-service for candles data to tinkoff-service");
        CandlesDto candles = stockServiceClient.getCandles(candlesRequestDto);
        if (candles == null || CollectionUtils.isEmpty(candles.getCandles())) {
            log.info("No candles found for requested data");
            throw new CandlesNotFoundException("No candles found for requested data");
        }
        return indicatorCalculator.calculateIndicators(candles.getCandles(), candlesRequestDto.getInterval());
    }

    private void setTinkoffApiToken() {
        TinkoffTokenDto tinkoffTokenDto = new TinkoffTokenDto();
        UserInfoDto userInfoDto = (UserInfoDto) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (userInfoDto.getTinkoffToken() != null) {
            String encodedToken = userInfoDto.getTinkoffToken();
            tinkoffTokenDto.setToken(EncryptionService.decrypt(encodedToken));
        } else {
            log.error("Tinkoff token invalid or does not exists for current user: {}, token: {}",
                    userInfoDto.getExternalId(), userInfoDto.getTinkoffToken());
            throw new SecurityException("Tinkoff API token not found");
        }
        stockServiceClient.setTinkoffToken(tinkoffTokenDto);
    }
}
