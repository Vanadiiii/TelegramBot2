package ru.dexsys.bot_service.yaml_configuration;

public enum RepositoryType {
    JPA,
    STAB;


    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
