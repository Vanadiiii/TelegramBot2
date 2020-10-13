package ru.dexsys.bot_service.service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bot.notification")
@Data
public class CongratulationConfiguration {
    private String time;
}
