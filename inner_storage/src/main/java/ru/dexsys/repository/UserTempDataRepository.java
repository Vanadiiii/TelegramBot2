package ru.dexsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dexsys.domain.IUserTempDataGateway;
import ru.dexsys.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserTempDataRepository extends IUserTempDataGateway, JpaRepository<UserEntity, Long> {
    @Override
    UserEntity save(UserEntity userEntity);


    @Override
    default Optional<UserEntity> getUserByChatId(long id) {
        return findById(id);
    }

    Optional<UserEntity> getUserEntityByPhone(String phone);

    @Override
    default Optional<UserEntity> getUserByPhone(String phone) {
        return getUserEntityByPhone(phone);
    }

    @Override
    default List<UserEntity> getUsers() {
        return findAll();
    }

    @Override
    default void delete(long id) {
        deleteById(id);
    }
}
