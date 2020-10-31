package ru.dexsys.bot_service.yaml_configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user-client")
@Data
public class UserClientConfiguration {
    private boolean isMock;
}


