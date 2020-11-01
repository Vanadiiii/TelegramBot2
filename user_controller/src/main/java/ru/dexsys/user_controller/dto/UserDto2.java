package ru.dexsys.user_controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto2 {
    private UUID id;
    private String firstName;
    private String secondName;
    private String middleName;
    private Date birthDay;
    private String phone;
    private String chatId;
    private boolean male;
}
