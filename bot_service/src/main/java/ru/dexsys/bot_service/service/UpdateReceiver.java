package ru.dexsys.bot_service.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.dexsys.bot_service.service.handler.AbstractHandler;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.domain.service.UserService;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateReceiver {
    private final List<AbstractHandler> handlers;

    private UserEntity user;

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        log.debug("Bot receive Update");
        try {
            if (isCommand(update)) {
                user = UserEntity.builder()
                        .id((long) update.getMessage().getFrom().getId())
                        .chatId(update.getMessage().getChatId())
                        .name(update.getMessage().getFrom().getUserName())
                        .build();
                String data = update.getMessage().getText();
                return getHandlerByCommand(data).handle(user, data);

            } else if (update.hasCallbackQuery()) {
                user = UserEntity.builder()
                        .id((long) update.getCallbackQuery().getFrom().getId())
                        .chatId(update.getCallbackQuery().getMessage().getChatId())
                        .name(update.getCallbackQuery().getFrom().getUserName())
                        .build();
                String data = update.getCallbackQuery().getData();
                return getHandlerByCallBackQuery(data).handle(user, data);
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (UnsupportedOperationException e) {
            log.info("User {} try to execute unsupported operation", user.getName());
        }
        return List.of(new SendMessage(user.getChatId(), "Unsupported operation. Use /help to see all commands"));
    }

    private AbstractHandler getHandlerByCommand(@NonNull String command) {
        return handlers.stream()
                .filter(AbstractHandler::isUserCommand)
                .filter(handler -> command.contains(handler.getCommand()))
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private AbstractHandler getHandlerByCallBackQuery(@NonNull String query) {
        return handlers.stream()
                .filter(handler -> !handler.isUserCommand())
                .filter(handler -> query.contains(handler.getCommand()))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isCommand(Update update) {
        return update.hasMessage() && update.getMessage().isCommand();
    }
}
