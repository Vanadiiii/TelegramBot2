package ru.dexsys.bot_service.service.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public abstract class AbstractHandler extends BotCommand {
    protected final Command command;
    protected final UserService userService;

    public AbstractHandler(Command command, UserService userService) {
        super(command.name().toLowerCase(), command.getDescription());
        this.command = command;
        this.userService = userService;
    }

    public abstract List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText);

    public abstract boolean isUserCommand();

    protected List<List<InlineKeyboardButton>> createKeyboard(
            List<String> elements, String buttonCommand, int high, int width
    ) {
        return Stream.iterate(0, n -> n + 1)
                .limit(high)
                .map(n -> Stream
                        .iterate(n + n * (width - 1), m -> m + 1)
                        .limit(width)
                        .filter(m -> m < elements.size())
                        .map(elements::get)
                        .map(InlineKeyboardButton::new)
                        .peek(button -> button.setCallbackData(buttonCommand + " " + button.getText()))
                        .collect(Collectors.toList())
                )
                .collect(Collectors.toList());
    }

    protected List<List<InlineKeyboardButton>> createKeyboard(String element, String buttonCommand) {
        return List.of(List.of(new InlineKeyboardButton(element).setCallbackData(buttonCommand)));
    }
}
