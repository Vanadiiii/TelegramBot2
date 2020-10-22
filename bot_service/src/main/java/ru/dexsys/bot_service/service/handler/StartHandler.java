package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.util.List;

@Component
@Slf4j
public class StartHandler extends AbstractHandler {
    public StartHandler(UserService userService) {
        super(Command.START, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User {} try to execute command '/start'", user.getName());
        if (!userService.hasUser(user)) {
            userService.save(user);
            log.info("User #" + user.getId() + " was saved into storage");
        }
        SendMessage message1 = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Hello! It's HappyBirthdayBot!\nI'll save your birthday and congratulate you)");
        SendMessage message2 = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Use '/help' command to see all commands");
        return List.of(message1, message2);
    }

    @Override
    public boolean isUserCommand() {
        return true;
    }
}
