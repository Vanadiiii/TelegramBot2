package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.dexsys.domain.IUserDomainService;
import ru.dexsys.domain.entity.UserEntity;

import java.io.Serializable;
import java.util.List;

@Component
@Slf4j
public class StartHandler extends AbstractHandler {
    public StartHandler(IUserDomainService userService) {
        super(Command.START, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User {} execute command '/start'", user.getChatId());
        if (!userService.hasUser(user.getChatId())) {
            userService.saveToTemp(user);
        }
        SendMessage message1 = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Hello! It's HappyBirthdayBot!\nI'll save your birthday and congratulate you)");
        SendMessage message2 = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Use /help command to see all commands");
        return List.of(message1, message2);
    }

    @Override
    public boolean isUserCommand() {
        return true;
    }
}
