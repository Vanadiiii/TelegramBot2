package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.dexsys.bot_service.service.util.TelegramKeyboardCreator;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.util.List;

@Component
@Slf4j
public class GetPhoneHandler extends AbstractHandler {
    public GetPhoneHandler(UserService userService) {
        super(Command.GET_PHONE, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User {} try to execute command '/get_phone'", user.getName());

        int dayOfBirth = Integer.parseInt(userText.split(" ")[1]);
        log.info("Saving day {} of user {}", dayOfBirth, user.getName());
        userService.updateDay(user.getId(), dayOfBirth);

        ReplyKeyboardMarkup markup = TelegramKeyboardCreator.savePhoneReplyKeyboard("Save your phone");
        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Now You can save your phone for my congratulations!")
                .setReplyMarkup(markup);

        return List.of(message);
    }


    @Override
    public boolean isUserCommand() {
        return false;
    }
}
