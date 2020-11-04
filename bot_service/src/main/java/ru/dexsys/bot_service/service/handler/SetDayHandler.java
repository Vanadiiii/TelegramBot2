package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.dexsys.domain.IUserDomainService;
import ru.dexsys.domain.entity.UserEntity;

import java.io.Serializable;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.dexsys.bot_service.service.util.TelegramKeyboardCreator.createInlineKeyboard;

@Component
@Slf4j
public class SetDayHandler extends AbstractHandler {
    public SetDayHandler(IUserDomainService userService) {
        super(Command.SET_DAY, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User #{} execute command '/set_day'", user.getChatId());
        Month monthOfBirth = Month.valueOf(userText.split(" ")[1].toUpperCase());

        log.info("Getting month of birth ({}) of user {}", monthOfBirth.getValue(), user.getName());

        List<String> listOfDays = Stream.iterate(1, n -> n + 1)
                .limit(monthOfBirth.length(true))
                .map(Objects::toString)
                .collect(Collectors.toList());
        InlineKeyboardMarkup keyboard = createInlineKeyboard(
                listOfDays,
                "success " + monthOfBirth.getValue(),
                6,
                6
        );

        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Chose the day of your birthday")
                .setReplyMarkup(keyboard);
        return List.of(message);
    }

    @Override
    public boolean isUserCommand() {
        return false;
    }
}
