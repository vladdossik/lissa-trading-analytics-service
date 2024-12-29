package lissa.trading.analytics.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = "lissa.trading")
public class LissaTradingAnalyticsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LissaTradingAnalyticsServiceApplication.class, args);
    }

}
