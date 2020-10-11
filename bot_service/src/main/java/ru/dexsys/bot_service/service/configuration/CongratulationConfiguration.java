package ru.dexsys.bot_service.service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@ConfigurationProperties(prefix = "congratulation")
@Data
public class CongratulationConfiguration {
    private LocalTime time;
}
