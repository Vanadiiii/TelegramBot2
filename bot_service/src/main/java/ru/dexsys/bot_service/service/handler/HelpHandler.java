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
public class HelpHandler extends AbstractHandler {
    public HelpHandler(IUserDomainService userService) {
        super(Command.HELP, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User #{} execute command '/help'", user.getChatId());

        SendMessage message = new SendMessage().setChatId(user.getChatId());

        String text;
        if (!userService.hasUser(user.getChatId())) {
            userService.saveToTemp(user);
            log.info("User #{} is unauthorized", user.getChatId());
            text = "Available command for you is '/authorize_by_phone' to check your identity";
        } else {
            text = "Available command for you is '/birthday' to set birthday";
        }

        return List.of(message.setText(text));
    }

    @Override
    public boolean isUserCommand() {
        return true;
    }
}
