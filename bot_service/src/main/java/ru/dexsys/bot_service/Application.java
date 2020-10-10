package ru.dexsys.bot_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication(scanBasePackages = {
        "ru.dexsys.bot_service",
        "ru.dexsys.domain",
        "ru.dexsys.repository"
})
public class Application {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(Application.class);
    }
}
