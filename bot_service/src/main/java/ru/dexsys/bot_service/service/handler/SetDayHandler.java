package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.dexsys.domain.entity.User;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class SetDayHandler extends AbstractHandler {
    public SetDayHandler(UserService userService) {
        super(userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String userText) {
        log.info("User {} try to execute command '/set_day'", user.getName());
        Month monthOfBirth = Month.valueOf(userText.split(" ")[1].toUpperCase());

        List<String> listOfDays = Stream.iterate(1, n -> n + 1)
                .limit(monthOfBirth.getValue())
                .map(Objects::toString)
                .collect(Collectors.toList());
        List<List<InlineKeyboardButton>> keyboard = createKeyboard(
                listOfDays, "finish", 6, 6
        );

        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Chose the day of your birthday")
                .setReplyMarkup(new InlineKeyboardMarkup(keyboard));
        return List.of(message);
    }

    @Override
    public Command operationIdentifier() {
        return Command.SET_DAY;
    }

    @Override
    public boolean isUserCommand() {
        return false;
    }
}
