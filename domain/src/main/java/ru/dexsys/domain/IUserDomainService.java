package ru.dexsys.domain;

import ru.dexsys.domain.entity.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IUserDomainService {
    UserEntity saveToTemp(UserEntity userEntity);

    List<UserEntity> getUsers();

    Optional<UserEntity> getUser(long chatId);

    Optional<UserEntity> getUserByPhone(String phone);

    void updateBirthday(Long chatId, Date birthday);

    void updatePhone(long chatId, String phone);

    void updateChatId(String phone, long chatId);

    void removeFromTemp(long chatId);

    boolean hasUser(long chatId);

    boolean hasUser(String phone);
}
