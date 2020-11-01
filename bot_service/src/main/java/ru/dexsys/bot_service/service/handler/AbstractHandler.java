package ru.dexsys.bot_service.service.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import ru.dexsys.bot_service.service.IHandler;
import ru.dexsys.domain.service.UserService;

@Component
public abstract class AbstractHandler extends BotCommand implements IHandler {
    protected final Command command;
    protected final UserService userService;

    public AbstractHandler(Command command, UserService userService) {
        super(command.name().toLowerCase(), command.getDescription());
        this.command = command;
        this.userService = userService;
    }
}
