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
    PRINT("print all users"),
    PRINT_MOCK("print all users from mock-controller"),
    INFO("show your info"),
    GET_PHONE("get your phone for receiving the congratulations"),
    ;

    private final String description;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
