package lissa.trading.analytics.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API для сервиса аналитики трейдинг ассистента")
                        .description("Этот API предоставляет методы для получения и анализа данных о торговых акциях, " +
                                "включая индикаторы и исторические данные, функционал будет пополняться ")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Дмитрий")
                                .url("https://t.me/kenpxrk")));
    }

    @Bean
    public GroupedOpenApi analyticsApi() {
        return GroupedOpenApi.builder()
                .group("analytics")
                .pathsToMatch("/v1/analytics/**")
                .build();
    }
}

