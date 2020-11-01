package ru.dexsys.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dexsys.domain.UserDataGateway;
import ru.dexsys.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
@ConditionalOnProperty(value = "repository.type", havingValue = "jpa")
public interface UserDataRepository extends UserDataGateway, JpaRepository<UserEntity, Long> {
    @Override
    @Modifying
    @Query("update UserEntity u set u.birthday.day = :day where u.id = :id")
    void updateDay(@Param("id") Long id, @Param("day") int day);

    @Override
    @Modifying
    @Query("update UserEntity u set u.birthday.month = :month where u.id = :id")
    void updateMonth(@Param("id") Long id, @Param("month") int month);

    @Override
    @Modifying
    @Query("update UserEntity u set u.phone = :phone where u.id = :id")
    void updatePhone(@Param("id") long userId, @Param("phone") String phone);

    @Override
    Optional<UserEntity> getUserByPhone(String phone);

    @Override
    UserEntity save(UserEntity userEntity);

    @Override
    default List<UserEntity> getUsers() {
        return findAll();
    }

    @Override
    default Optional<UserEntity> getUserById(long id) {
        return findById(id);
    }

    @Override
    default void delete(Long id) {
        deleteById(id);
    }
}
