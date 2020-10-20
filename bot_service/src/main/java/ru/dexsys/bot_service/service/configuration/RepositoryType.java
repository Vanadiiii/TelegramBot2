package ru.dexsys.bot_service.service.configuration;

public enum RepositoryType {
    JPA,
    STAB;


    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
