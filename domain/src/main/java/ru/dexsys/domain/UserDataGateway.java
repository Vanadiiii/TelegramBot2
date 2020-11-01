package ru.dexsys.domain;

import org.springframework.stereotype.Component;
import ru.dexsys.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Component
public interface UserDataGateway {

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> getUserById(long id);

    Optional<UserEntity> getUserByPhone(String phone);

    List<UserEntity> getUsers();

    void updateDay(Long id, int day);

    void updateMonth(Long id, int day);

    void updatePhone(long userId, String phone);

    void delete(Long id);
}
