package ru.dexsys.bot_service.yaml_configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "bot")
@Data
public class BotConfiguration {
    @NotEmpty
    private String name;
    @NotEmpty
    private String token;
    private CongratulationConfiguration notification;
}

