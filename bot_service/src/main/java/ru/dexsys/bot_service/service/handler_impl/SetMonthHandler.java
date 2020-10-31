package ru.dexsys.bot_service.service.handler_impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.time.Month;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static ru.dexsys.bot_service.service.util.TelegramKeyboardCreator.*;

@Component
@Slf4j
public class SetMonthHandler extends AbstractHandler {
    public SetMonthHandler(UserService userService) {
        super(Command.SET_MONTH, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User {} try to execute command '/set_month'", user.getName());

        List<String> listOfMonth = EnumSet.allOf(Month.class)
                .stream()
                .map(month -> month.name().toLowerCase())
                .collect(Collectors.toList());
        InlineKeyboardMarkup keyboard = createInlineKeyboard(
                listOfMonth, "set_day", 3, 4
        );

        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Chose the month of your birthday")
                .setReplyMarkup(keyboard);
        return List.of(message);
    }

    @Override
    public boolean isUserCommand() {
        return false;
    }
}
