package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class SetDayHandler extends AbstractHandler {
    public SetDayHandler(UserService userService) {
        super(Command.SET_DAY, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User {} try to execute command '/set_day'", user.getName());
        Month monthOfBirth = Month.valueOf(userText.split(" ")[1].toUpperCase());

        log.info("Saving month {} of user {}", monthOfBirth.getValue(), user.getName());
        userService.updateMonth(user.getId(), monthOfBirth.getValue());

        List<String> listOfDays = Stream.iterate(1, n -> n + 1)
                .limit(monthOfBirth.length(true))
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
    public boolean isUserCommand() {
        return false;
    }
}
