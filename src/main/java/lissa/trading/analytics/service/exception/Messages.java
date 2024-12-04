package lissa.trading.analytics.service.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Messages {
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public String getMessage(String key, Object... args) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
        Locale locale = localeResolver.resolveLocale(request);
        System.out.println("LOCALE: " + locale);
        return messageSource.getMessage(key, args, locale);
    }
}

