package ru.dexsys.user_controller.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

import java.time.Duration;

@Configuration
public class MockControllerConfig {
    @Bean
    public RestOperations restOperations() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMinutes(1))
                .setReadTimeout(Duration.ofMinutes(1))
                .build();
    }
}
