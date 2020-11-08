package ru.dexsys.bot_service.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.dexsys.domain.IUserDomainService;
import ru.dexsys.domain.entity.UserEntity;

import java.io.Serializable;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SuccessHandler extends AbstractHandler {
    public SuccessHandler(IUserDomainService userService) {
        super(Command.SUCCESS, userService);
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String birthInfo) {
        String[] rawBirthData = birthInfo.split(" ");
        int month = Integer.parseInt(rawBirthData[1]) - 1;
        int day = Integer.parseInt(rawBirthData[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, month, day);
        Date birthday = calendar.getTime();

        userService.updateBirthday(user.getChatId(), birthday);

        log.info("User #{} save his birthday to corporate storage- '{}'", user.getChatId(), birthday);

        return List.of(new SendMessage()
                .setChatId(user.getChatId())
                .setText("SUCCESS!\n Now, relax and wait the congratulations)")
        );
    }

    @Override
    public boolean isUserCommand() {
        return false;
    }
}
