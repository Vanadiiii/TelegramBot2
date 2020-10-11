package ru.dexsys.bot_service.service.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.dexsys.domain.entity.User;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractHandler extends BotCommand {
    protected final Command command;
    protected final UserService userService;

    public AbstractHandler(Command command, UserService userService) {
        super(command.name().toLowerCase(), command.getDescription());
        this.command = command;
        this.userService = userService;
    }

    public abstract List<PartialBotApiMethod<? extends Serializable>> handle(User user, String userText);

    public abstract boolean isUserCommand();

    protected List<List<InlineKeyboardButton>> createKeyboard(
            List<String> elements, String buttonCommand, int width, int length
    ) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int j = 0; j < length; j++) {
                int elemNumber = j + i * length;
                if (elemNumber < elements.size()) {
                    String elementValue = elements.get(elemNumber);
                    row.add(new InlineKeyboardButton(elementValue)
                            .setCallbackData(buttonCommand + " " + elementValue)
                    );
                }
            }
            rows.add(row);
        }
        return rows;
    }

    protected List<List<InlineKeyboardButton>> createKeyboard(String element, String buttonCommand) {
        return List.of(List.of(new InlineKeyboardButton(element).setCallbackData(buttonCommand)));
    }
}
