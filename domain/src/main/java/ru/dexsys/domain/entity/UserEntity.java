package ru.dexsys.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserEntity {
    private Long chatId;
    private String firstName;
    private String secondName;
    private String name;
    private Date birthday;
    private String phone;

    public UserEntity(String name, long chatId) {
        this.chatId = chatId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                ", \nchatId = " + chatId +
                ", \nname = '" + name + '\'' +
                ", \nfirstName = '" + firstName + '\'' +
                ", \nsecondName = '" + secondName + '\'' +
                ", \nbirthday = " + birthdayToString() +
                ", \nphone = '" + phone + '\'' +
                "\n}";
    }

    private String birthdayToString() {
        return new SimpleDateFormat("MMMM dd", Locale.UK)
                .format(birthday);
    }
}