package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.dexsys.domain.IUserDomainService;
import ru.dexsys.domain.entity.UserEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@Slf4j
public class PrintHandler extends AbstractHandler {
    public PrintHandler(IUserDomainService userService) {
        super(Command.PRINT, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User #{} execute command '/print'", user.getChatId());

        List<SendMessage> usersInfo;
        if (!userService.hasUser(user.getChatId())) {
            userService.saveToTemp(user);
            log.info("User #" + user.getChatId() + " was saved into temp storage");
            usersInfo = List.of(new SendMessage(user.getChatId(), user.toString()));
        } else {
            usersInfo = userService.getUsers()
                    .stream()
                    .map(UserEntity::toString)
                    .map(info -> new SendMessage(user.getChatId(), info))
                    .collect(toList());
        }
        SendMessage title = new SendMessage()
                .setChatId(user.getChatId())
                .setText("There are all users:");
        usersInfo.add(0, title);

        return new ArrayList<>(usersInfo);
    }

    @Override
    public boolean isUserCommand() {
        return true;
    }
}
