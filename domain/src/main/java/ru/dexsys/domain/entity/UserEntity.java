package ru.dexsys.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String name;
    private Long chatId;
    private Birthday birthday;

    public UserEntity(long id, String name, long chatId) {
        this.id = id;
        this.name = name;
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", chatId = " + chatId +
                ", birthday = " + birthday +
                '}';
    }
}