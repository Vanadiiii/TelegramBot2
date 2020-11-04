package ru.dexsys.domain;

import org.springframework.stereotype.Component;
import ru.dexsys.domain.entity.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public interface IUserDataGateway {

    Optional<UserEntity> getUserByChatId(long id);

    Optional<UserEntity> getUserByPhone(String phone);

    List<UserEntity> getUsers();

    void updatePhone(long userId, String phone);

    void updateBirthday(Long userId, Date birthday);

    void updateChatId(String phone, long chatId);

    UserEntity generate();
}
