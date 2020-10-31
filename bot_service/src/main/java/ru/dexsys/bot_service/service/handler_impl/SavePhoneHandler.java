package ru.dexsys.bot_service.service.handler_impl;

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
public class SavePhoneHandler extends AbstractHandler {
    public SavePhoneHandler(UserService userService) {
        super(Command.SAVE_PHONE, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String phoneNumber) {
        log.info("User {} try to execute command '/save_phone'", user.getName());

        userService.updatePhone(user.getId(), phoneNumber);

        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Thank you! Now your can relax and wait the congratulations)");
        return List.of(message);
    }

    @Override
    public boolean isUserCommand() {
        return false;
    }
}
