package ru.dexsys.domain;

import org.springframework.stereotype.Component;
import ru.dexsys.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Component
public interface UserDataGateway {

    void updateDay(Long id, int day);

    void updateMonth(Long id, int day);

    UserEntity save(UserEntity userEntity);

    List<UserEntity> getUsers();

    Optional<UserEntity> getUserById(long id);

    void delete(Long id);

    void updatePhone(long userId, String phone);
}
