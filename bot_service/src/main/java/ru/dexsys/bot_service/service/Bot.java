package ru.dexsys.bot_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.dexsys.bot_service.service.bot_options.BotOptions;

@Slf4j
@Service
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    private final UpdateReceiver updateReceiver;

    public Bot(UpdateReceiver updateReceiver, BotOptions botOptions) {
        super(botOptions);
        this.updateReceiver = updateReceiver;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var messagesToSend = updateReceiver.handle(update);

        if (messagesToSend != null && !messagesToSend.isEmpty()) {
            messagesToSend.forEach(response -> {
                        if (response instanceof SendMessage) {
                            try {
                                execute((SendMessage) response);
                            } catch (TelegramApiException e) {
                                log.error("Error with executing this message:", e);
                            }
                        }
                    }
            );
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
