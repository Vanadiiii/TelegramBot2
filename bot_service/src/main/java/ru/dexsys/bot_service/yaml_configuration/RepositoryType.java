package ru.dexsys.bot_service.yaml_configuration;

public enum RepositoryType {
    JPA,
    STAB,
    MOCK;


    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
