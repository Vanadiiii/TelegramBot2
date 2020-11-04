package ru.dexsys.bot_service.yaml_configuration;

public enum RepositoryType {
    JPA,
    STAB,
    MOCK,
    GOOGLE_SHEET;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
