package lissa.trading.analytics.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API для анализа данных ценных бумаг")
                        .description("Этот API предоставляет методы для анализа различных показателей ценных бумаг, " +
                                "расчета технических индикаторов и тд.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Dmitry Grishin")
                                .url("https://t.me/kenpxrk"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("indicators")
                .pathsToMatch("/v1/api/analytics/**")
                .build();
    }
}
