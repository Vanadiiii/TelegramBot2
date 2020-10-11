package ru.dexsys.bot_service.service.handler;

public enum Command {
    START,
    HELP,
    BIRTHDAY,
    SET_DAY,
    SET_MONTH,
    ;


    @Override
    public String toString() {
        return "/" + this.name().toLowerCase();
    }
}
