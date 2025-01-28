package lissa.trading.analytics.service.security.internal;

import jakarta.annotation.PostConstruct;
import lissa.trading.analytics.service.config.IntegrationServicesProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Slf4j
@Component
@RequiredArgsConstructor
public class InternalTokenService {

    private final IntegrationServicesProperties integrationServicesProperties;

    private final Map<String, String> urlToToken = new HashMap<>();

    @PostConstruct
    public void init() {
        integrationServicesProperties.getServices().forEach((serviceName, serviceConfig) -> {
            String token = serviceConfig.getToken();
            if (token != null) {
                urlToToken.put(serviceConfig.getUrl(), token);
            }
        });
    }

    public String getTokenByUrl(String url) {
        return urlToToken.get(url);
    }
}
