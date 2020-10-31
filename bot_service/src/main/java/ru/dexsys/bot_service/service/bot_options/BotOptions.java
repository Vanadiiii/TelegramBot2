package ru.dexsys.bot_service.service.bot_options;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import javax.annotation.PostConstruct;

@Component
public class BotOptions extends DefaultBotOptions {
    @Value("${proxy.enable}")
    boolean proxyEnable;
    @Value("${proxy.host}")
    private String proxyHost;
    @Value("${proxy.port}")
    private Integer proxyPort;
    @Value("${proxy.type}")
    private ProxyType proxyType;

    @PostConstruct
    public void init() {
        if (proxyEnable) {
            this.setProxyHost(proxyHost);
            this.setProxyPort(proxyPort);
            this.setProxyType(proxyType);

            System.setProperty("http.proxyHost", proxyHost);
            System.setProperty("http.proxyPort", proxyPort.toString());
        }
    }
}
