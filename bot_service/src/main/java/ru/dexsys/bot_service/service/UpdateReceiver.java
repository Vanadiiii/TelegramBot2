package ru.dexsys.bot_service.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.dexsys.bot_service.service.handler.AbstractHandler;
import ru.dexsys.domain.entity.User;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateReceiver {
    private final List<AbstractHandler> handlers;
    private final UserService userService;

    private Message message;
    private long chatId;
    private User user;
    private String data;


    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        log.info("Bot receive Update");
        try {
            AbstractHandler handler;
            if (isCommand(update)) {
                fillDataFromCommand(update);
                handler = getHandlerByCommand(data);

            } else if (update.hasCallbackQuery()) {
                fillDataFromCallback(update);
                handler = getHandlerByCallBackQuery(data);
            } else {
                throw new UnsupportedOperationException();
            }
            return handler.handle(user, data);
        } catch (UnsupportedOperationException e) {
            log.info("User {} try to execute unsupported operation", user.getName());
        }
        return List.of(new SendMessage(chatId, "Unsupported operation. Use /help to see all commands"));
    }

    private void fillDataFromCommand(Update update) {
        this.message = update.getMessage();
        chatId = message.getChatId();
        var botUser = message.getFrom();
        data = message.getText();
        user = userService
                .getUser(botUser.getId())
                .orElseGet(() ->
                        userService.save(new User(botUser.getId(), botUser.getUserName(), chatId))
                );
    }

    private void fillDataFromCallback(Update update) {
        CallbackQuery callback = update.getCallbackQuery();
        this.message = callback.getMessage();
        chatId = message.getChatId();
        var botUser = callback.getFrom();
        data = callback.getData();
        user = userService
                .getUser(botUser.getId())
                .orElseGet(() ->
                        userService.save(new User(botUser.getId(), botUser.getUserName(), chatId))
                );
    }

    private AbstractHandler getHandlerByCallBackQuery(@NonNull String query) {
        return handlers.stream()
                .filter(handler -> !handler.isUserCommand())
                .filter(handler -> query.contains(handler.getCommand()))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private AbstractHandler getHandlerByCommand(@NonNull String command) {
        return handlers.stream()
                .filter(AbstractHandler::isUserCommand)
                .filter(handler -> command.contains(handler.getCommand()))
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }

    private boolean isCommand(Update update) {
        return update.hasMessage() && update.getMessage().isCommand();
    }
}
