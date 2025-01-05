package lissa.trading.analytics.service.security;

import lissa.trading.lissa.auth.lib.dto.UserInfoDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextHelper {

    public static UserInfoDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserInfoDto) {
            return (UserInfoDto) authentication.getPrincipal();
        }
        return null;
    }
}
