package ru.filini.expensetrackerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springdoc.core.GroupedOpenApi;

@SpringBootApplication
public class ExpenseTrackerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/transactions/**", "/expense-limits/**", "/exchange-rates/**")
                .build();
    }
}
