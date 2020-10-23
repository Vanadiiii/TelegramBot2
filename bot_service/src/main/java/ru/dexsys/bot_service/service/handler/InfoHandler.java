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
public class InfoHandler extends AbstractHandler {
    public InfoHandler(UserService userService) {
        super(Command.INFO, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User {} try to execute command '/info'", user.getName());
        if (!userService.hasUser(user)) {
            userService.save(user);
            log.info("User #" + user.getId() + " was saved into storage");
        }
        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Your info:\n" + user.toString());
        return List.of(message);
    }

    @Override
    public boolean isUserCommand() {
        return true;
    }
}
