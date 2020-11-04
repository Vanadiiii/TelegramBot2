package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.dexsys.domain.IUserDomainService;
import ru.dexsys.domain.entity.UserEntity;

import java.io.Serializable;
import java.util.List;

import static ru.dexsys.bot_service.service.util.TelegramKeyboardCreator.createInlineKeyboard;

@Component
@Slf4j
public class BirthdayHandler extends AbstractHandler {
    public BirthdayHandler(IUserDomainService userService) {
        super(Command.BIRTHDAY, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User #{} execute command '/birthday'", user.getChatId());

        SendMessage message = new SendMessage().setChatId(user.getChatId());

        String text;
        if (!userService.hasUser(user.getChatId())) {
            userService.saveToTemp(user);
            log.warn("Unauthorized User #{} try to execute command '/birthday'", user.getChatId());
            text = "This command unavailable for you.\nPlease, execute /authorize_by_phone command";
        } else {
            message.setReplyMarkup(createInlineKeyboard("Save your birthday", "set_month"));
            text = "Start to saving birthday...)";
        }

        return List.of(message.setText(text));
    }

    @Override
    public boolean isUserCommand() {
        return true;
    }
}
