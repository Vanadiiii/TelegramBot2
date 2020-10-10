package ru.dexsys.bot_service.service;

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


    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        try {
            if (isCommand(update)) {
                fillReceiverData(update.getMessage());
                return getHandlerByCommand(message.getText()).handle(user, message.getText());

            } else if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                fillReceiverData(callbackQuery.getMessage());
                return getHandlerByCallBackQuery(callbackQuery.getData()).handle(user, callbackQuery.getData());
            }
        } catch (UnsupportedOperationException e) {
            log.info("User {} try to execute unsupported operation", user.getName());
        }
        return List.of(new SendMessage(chatId, "Unsupported operation. Use /help to see all commands"));
    }

    private void fillReceiverData(Message message) {
        this.message = message;
        chatId = message.getChatId();
        var botUser = message.getFrom();
        user = userService
                .getUser(botUser.getId())
                .orElseGet(() ->
                        userService.save(new User(botUser.getId(), botUser.getUserName(), chatId))
                );
    }

    private AbstractHandler getHandlerByCallBackQuery(String query) {
        return handlers.stream()
                .filter(handler -> !handler.isUserCommand())
                .filter(handler -> handler.operationIdentifier().startsWith(query))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private AbstractHandler getHandlerByCommand(String command) {
        return handlers.stream()
                .filter(AbstractHandler::isUserCommand)
                .filter(handler -> handler.operationIdentifier().startsWith(command))
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
