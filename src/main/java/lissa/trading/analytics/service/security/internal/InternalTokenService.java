package lissa.trading.analytics.service.security.internal;

import jakarta.annotation.PostConstruct;
import lissa.trading.analytics.service.config.IntegrationServicesProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Slf4j
@Component
@RequiredArgsConstructor
public class InternalTokenService {

    private final IntegrationServicesProperties integrationServicesProperties;

    private final Map<String, String> urlToToken = new HashMap<>();

    @Value("${security.internal.token}")
    private String internalToken;


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

    protected boolean validateInternalToken(String token) {
        log.info("Validating token {}", token);
        if (token.isEmpty()) {
            return false;
        }
        return new String(Base64.getDecoder().decode(internalToken)).trim().equals(token);
    }

    protected String getServiceNameFromToken(String token) {
        return token;
    }

    protected List<String> getRolesFromToken(String token) {
        return validateInternalToken(token)
                ? Collections.singletonList("ROLE_INTERNAL_SERVICE")
                : Collections.emptyList();
    }
}
