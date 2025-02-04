package lissa.trading.analytics.service.config;

import feign.RequestInterceptor;
import lissa.trading.analytics.service.client.tinkoff.feign.InternalTokenFeignInterceptor;
import lissa.trading.analytics.service.security.internal.InternalTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class InternalFeignConfig {

    private final InternalTokenService tokenService;

    @Bean
    public RequestInterceptor internalTokenInterceptor() {
        return new InternalTokenFeignInterceptor(tokenService);
    }
}
