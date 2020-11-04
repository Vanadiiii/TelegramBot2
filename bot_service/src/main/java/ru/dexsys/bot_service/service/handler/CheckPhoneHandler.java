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
public class CheckPhoneHandler extends AbstractHandler {
    public CheckPhoneHandler(IUserDomainService userService) {
        super(Command.CHECK_PHONE, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String phoneNumber) {
        SendMessage message = new SendMessage().setChatId(user.getChatId());

        if (userService.hasUser(phoneNumber)) {
            userService.updateChatId(phoneNumber, user.getChatId());
            userService.removeFromTemp(user.getChatId());
            message.setText("You are authorized now.\n" +
                    "Available command for you is /birthday " +
                    "to save your birthday in corporate storage");
        } else {
            log.warn("User #{} is unauthorized", user.getChatId());
            message.setText("There are no such phone in corporate storage.\n" +
                    "Please, contact with your HR managers");
        }
        return List.of(message);
    }

    @Override
    public boolean isUserCommand() {
        return false;
    }
}
