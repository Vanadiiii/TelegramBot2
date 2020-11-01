package ru.dexsys.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserEntity {
    private Long id;
    private String name;
    private Long chatId;
    private Birthday birthday;
    private String phone;
    private String firstName;
    private String secondName;

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
                ", firstName = '" + firstName + '\'' +
                ", secondName = '" + secondName + '\'' +
                ", phone = '" + phone + '\'' +
                '}';
    }

    public UserEntity setDay(@NonNull int day) {
        if (this.getBirthday() != null) {
            this.getBirthday().setDay(day);
        } else {
            this.setBirthday(new Birthday(day, null));
        }
        return this;
    }

    public UserEntity setMonth(@NonNull int month) {
        if (this.getBirthday() != null) {
            this.getBirthday().setMonth(month);
        } else {
            this.setBirthday(new Birthday(null, month));
        }
        return this;
    }
}