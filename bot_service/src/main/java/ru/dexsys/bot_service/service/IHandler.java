package ru.dexsys.bot_service.service;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import ru.dexsys.domain.entity.UserEntity;

import java.io.Serializable;
import java.util.List;

public interface IHandler {

    List<PartialBotApiMethod<? extends Serializable>> handle(UserEntity user, String userText);

    boolean isUserCommand();
}
