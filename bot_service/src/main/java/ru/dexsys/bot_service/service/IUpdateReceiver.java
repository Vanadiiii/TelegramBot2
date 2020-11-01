package ru.dexsys.bot_service.service;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.List;

public interface IUpdateReceiver {
    List<PartialBotApiMethod<? extends Serializable>> handle(Update update);
}
