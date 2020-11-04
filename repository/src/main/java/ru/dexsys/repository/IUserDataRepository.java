package ru.dexsys.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dexsys.domain.IUserDataGateway;
import ru.dexsys.domain.entity.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
@ConditionalOnProperty(value = "repository.type", havingValue = "jpa")
public interface IUserDataRepository extends IUserDataGateway, JpaRepository<UserEntity, Long> {

    @Override
    Optional<UserEntity> getUserByPhone(String phone);

    @Override
    default Optional<UserEntity> getUserByChatId(long id) {
        return findById(id);
    }

    @Override
    default List<UserEntity> getUsers() {
        return findAll();
    }

    @Override
    @Modifying
    @Query("update UserEntity u set u.phone = :phone where u.chatId = :chatId")
    void updatePhone(@Param("chatId") long chatId, @Param("phone") String phone);

    @Override
    @Modifying
    @Query("update UserEntity u set u.birthday = :birthday where u.chatId = :chatId")
    void updateBirthday(@Param("chatId") Long chatId, @Param("birthday") Date birthday);

    @Override
    @Modifying
    @Query("update UserEntity u set u.chatId = :chatId where u.phone = :phone")
    void updateChatId(@Param("phone") String phone, @Param("chatId") long chatId);
}
