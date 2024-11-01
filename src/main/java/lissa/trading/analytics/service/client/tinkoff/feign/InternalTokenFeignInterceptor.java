package lissa.trading.analytics.service.client.tinkoff.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InternalTokenFeignInterceptor implements RequestInterceptor {

    @Value("${security.internal.token}")
    private String internalToken;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + internalToken);
    }
}
