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
public class HelpHandler extends AbstractHandler {
    public HelpHandler(UserService userService) {
        super(Command.HELP, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User {} try to execute command '/help'", user.getName());
        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Available command for you is '/birthday' to set birthday");
        return List.of(message);
    }

    @Override
    public boolean isUserCommand() {
        return true;
    }
}
