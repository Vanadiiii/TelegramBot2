package ru.dexsys.bot_service.yaml_configuration;

public enum RepositoryType {
    //    JPA,
    STAB,
    //    GOOGLE_SHEET
    MOCK;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
