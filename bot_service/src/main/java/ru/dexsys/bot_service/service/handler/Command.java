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
    SAVE_PHONE("save your phone"),
    AUTHORIZE_BY_PHONE("check your authorization by phone"),
    CHECK_PHONE("check your phone in corporate storage"),
    PRINT("print all users"),
    INFO("show your info"),
    SUCCESS("successfully finish of your congratulations process"),
    ;

    private final String description;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
