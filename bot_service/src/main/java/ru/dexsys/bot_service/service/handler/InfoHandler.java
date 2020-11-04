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
public class InfoHandler extends AbstractHandler {
    public InfoHandler(IUserDomainService userService) {
        super(Command.INFO, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User #{} execute command '/info'", user.getChatId());
        if (!userService.hasUser(user.getChatId())) {
            userService.saveToTemp(user);
            log.info("User #" + user.getChatId() + " was saved into temp storage");
        }
        UserEntity savedUser = userService.getUser(user.getChatId())
                .orElseThrow(() -> new RuntimeException("Not found User #" + user.getChatId()));
        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Your info:\n" + savedUser);
        return List.of(message);
    }

    @Override
    public boolean isUserCommand() {
        return true;
    }
}
