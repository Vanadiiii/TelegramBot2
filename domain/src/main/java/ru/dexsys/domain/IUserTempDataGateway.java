package ru.dexsys.domain;

import org.springframework.stereotype.Component;
import ru.dexsys.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Component
public interface IUserTempDataGateway {

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> getUserByChatId(long id);

    Optional<UserEntity> getUserByPhone(String phone);

    List<UserEntity> getUsers();

    void delete(long id);
}
