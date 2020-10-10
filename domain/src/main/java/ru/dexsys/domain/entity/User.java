package ru.dexsys.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private Long chatId;
    private Birthday birthday;

    public User(long id, String name, long chatId) {
        this.id = id;
        this.name = name;
        this.chatId = chatId;
    }
}