package ru.dexsys.user_controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDto {
    private Date birthDay;
    private String chatId;
    private String phone;
}
