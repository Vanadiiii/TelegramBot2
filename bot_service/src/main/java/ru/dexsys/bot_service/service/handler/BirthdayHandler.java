package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.util.List;

@Component
@Slf4j
public class BirthdayHandler extends AbstractHandler {
    public BirthdayHandler(UserService userService) {
        super(Command.BIRTHDAY, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User {} try to execute command '/birthday'", user.getName());
        if (!userService.hasUser(user)) {
            userService.save(user);
            log.info("User #" + user.getId() + " was saved into storage");
        }

        var keyboard = createKeyboard("Save your birthday", "set_month");

        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Start saving birthday process")
                .setReplyMarkup(new InlineKeyboardMarkup(keyboard));
        return List.of(message);
    }

    @Override
    public boolean isUserCommand() {
        return true;
    }
}
