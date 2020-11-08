package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.dexsys.bot_service.service.util.TelegramKeyboardCreator;
import ru.dexsys.domain.IUserDomainService;
import ru.dexsys.domain.entity.UserEntity;

import java.io.Serializable;
import java.util.List;

@Component
@Slf4j
public class AuthorizeByPhoneHandler extends AbstractHandler {
    public AuthorizeByPhoneHandler(IUserDomainService userService) {
        super(Command.AUTHORIZE_BY_PHONE, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {

        ReplyKeyboardMarkup markup = TelegramKeyboardCreator.savePhoneReplyKeyboard(
                "Send your phone automatically",
                "Write your phone\nonly digits, please"
        );
        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Now You can save your phone for my congratulations!")
                .setReplyMarkup(markup);

        return List.of(message);
    }

    @Override
    public boolean isUserCommand() {
        return true;
    }
}
