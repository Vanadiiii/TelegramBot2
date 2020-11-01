package ru.dexsys.bot_service.service.update_receiver;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.dexsys.bot_service.service.IUpdateReceiver;
import ru.dexsys.bot_service.service.handler.AbstractHandler;
import ru.dexsys.domain.entity.UserEntity;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateReceiverImpl implements IUpdateReceiver {
    private final List<AbstractHandler> handlers;

    private UserEntity user;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        log.debug("Bot receive Update");
        try {
            if (isCommand(update)) {
                user = createFromMessage(update);
                String data = update.getMessage()
                        .getText()
                        .replace("/", "");
                return getHandlerByCommand(data).handle(user, data);

            } else if (update.hasMessage() && update.getMessage().hasContact()) {
                user = createFromMessage(update);
                String data = update.getMessage()
                        .getContact()
                        .getPhoneNumber();
                return getHandlerForSavingContact().handle(user, data);

            } else if (update.hasCallbackQuery()) {
                user = createFromCallback(update);
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

    private UserEntity createFromCallback(Update update) {
        return UserEntity.builder()
                .id((long) update.getCallbackQuery().getFrom().getId())
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .name(update.getCallbackQuery().getFrom().getUserName())
                .build()
                .setFirstName(update.getCallbackQuery().getFrom().getFirstName())
                .setSecondName(update.getCallbackQuery().getFrom().getLastName());
    }

    private UserEntity createFromMessage(Update update) {
        return UserEntity.builder()
                .id((long) update.getMessage().getFrom().getId())
                .chatId(update.getMessage().getChatId())
                .name(update.getMessage().getFrom().getUserName())
                .build()
                .setFirstName(update.getMessage().getFrom().getFirstName())
                .setSecondName(update.getMessage().getFrom().getLastName());
    }

    private AbstractHandler getHandlerForSavingContact() {
        return handlers.stream()
                .filter(handler -> handler.getCommand().equals("save_phone"))
                .findFirst()
                .orElseThrow();
    }

    private AbstractHandler getHandlerByCommand(@NonNull String command) {
        return handlers.stream()
                .filter(AbstractHandler::isUserCommand)
                .filter(handler -> command.equals(handler.getCommand()))
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
