package ru.dexsys.bot_service.service.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import ru.dexsys.bot_service.service.IHandler;
import ru.dexsys.domain.IUserDomainService;

@Component
public abstract class AbstractHandler extends BotCommand implements IHandler {
    protected final Command command;
    protected final IUserDomainService userService;

    public AbstractHandler(Command command, IUserDomainService userService) {
        super(command.name().toLowerCase(), command.getDescription());
        this.command = command;
        this.userService = userService;
    }
}
