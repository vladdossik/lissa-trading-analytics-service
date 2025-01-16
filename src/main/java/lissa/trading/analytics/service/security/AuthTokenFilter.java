package lissa.trading.analytics.service.security;

import lissa.trading.lissa.auth.lib.dto.UserInfoDto;
import lissa.trading.lissa.auth.lib.feign.AuthServiceClient;
import lissa.trading.lissa.auth.lib.security.BaseAuthTokenFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends BaseAuthTokenFilter<UserInfoDto> {

    private final AuthServiceClient authServiceClient;

    @Override
    protected List<String> parseRoles(UserInfoDto userInfoDto) {
        return userInfoDto.getRoles();
    }

    @Override
    protected UserInfoDto retrieveUserInfo(String token) {
        try {
            log.info("Trying to retrieve user info from token {}", token);
            UserInfoDto userInfoDto = authServiceClient.getUserInfo("Bearer " + token);
            log.info("userInfoDto: {}", userInfoDto);
            if (userInfoDto != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userInfoDto, token, List.of());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            return userInfoDto;
        } catch (Exception ex) {
            log.error("Failed to retrieve user info from auth service: {}", ex.getMessage());
            log.error(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }}