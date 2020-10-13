package ru.dexsys.bot_service.service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Component
@ConfigurationProperties(prefix = "proxy")
@Data
public class ProxyConfiguration {
    private boolean enable;
    private String host;
    private int port;
    private DefaultBotOptions.ProxyType type;
}


