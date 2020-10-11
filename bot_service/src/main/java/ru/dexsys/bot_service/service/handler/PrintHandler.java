package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.dexsys.domain.entity.User;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PrintHandler extends AbstractHandler {
    public PrintHandler(UserService userService) {
        super(Command.PRINT, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String userText) {
        log.info("User {} try to execute command '/print'", user.getName());

        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("There are all users:");
        SendMessage usersInfo = new SendMessage()
                .setChatId(user.getChatId())
                .setText(
                        userService.getUsers()
                                .stream()
                                .map(User::toString)
                                .collect(Collectors.joining(";"))
                );
        return List.of(message, usersInfo);
    }

    @Override
    public boolean isUserCommand() {
        return true;
    }
}
