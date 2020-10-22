package ru.dexsys.bot_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.dexsys.UserController;

@SpringBootApplication(scanBasePackages = "ru.dexsys")
public class Application {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        var context = SpringApplication.run(Application.class, args);

        var bean = context.getBean(UserController.class);
    }
}
