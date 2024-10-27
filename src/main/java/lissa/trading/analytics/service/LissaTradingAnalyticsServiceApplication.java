package lissa.trading.analytics.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LissaTradingAnalyticsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LissaTradingAnalyticsServiceApplication.class, args);
	}

}
