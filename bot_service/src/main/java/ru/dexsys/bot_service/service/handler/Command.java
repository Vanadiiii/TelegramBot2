package ru.dexsys.bot_service.service.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Command {
    START("send start message"),
    HELP("show available commands"),
    BIRTHDAY("save your birthday"),
    SET_DAY("save your birthday day"),
    SET_MONTH("save your birthday month"),
    FINISH("finish the birthday saving process"),
    PRINT("print all users"),
    ;

    private final String description;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
