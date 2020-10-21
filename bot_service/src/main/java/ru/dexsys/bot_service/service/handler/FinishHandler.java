package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.util.List;

@Component
@Slf4j
public class FinishHandler extends AbstractHandler {
    public FinishHandler(UserService userService) {
        super(Command.FINISH, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText) {
        log.info("User {} try to execute command '/finish'", user.getName());

        int dayOfBirth = Integer.parseInt(userText.split(" ")[1]);
        log.info("Saving day {} of user {}", dayOfBirth, user.getName());
        userService.updateDay(user.getId(), dayOfBirth);

        SendMessage message = new SendMessage()
                .setChatId(user.getChatId())
                .setText("Thank you! Now your can relax and wait the congratulations)");
        return List.of(message);
    }

    @Override
    public boolean isUserCommand() {
        return false;
    }
}
