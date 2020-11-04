package ru.dexsys.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.dexsys.domain.IUserTempDataGateway;
import ru.dexsys.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public class UserTempRepositoryImpl implements IUserTempDataGateway {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserEntity save(UserEntity userEntity) {
        jdbcTemplate.update(
                "insert into USERS_TEMP " +
                        "(chat_id, first_name, second_name, name, birthday, phone) " +
                        "values ( ?, ?, ?, ?, ?, ? )",
                userEntity.getChatId(),
                userEntity.getFirstName(),
                userEntity.getSecondName(),
                userEntity.getName(),
                userEntity.getBirthday(),
                userEntity.getPhone()
        );
        return userEntity;
    }

    @Override
    public Optional<UserEntity> getUserByChatId(long chatId) {
        return jdbcTemplate.query(
                "select * from USERS_TEMP where chat_id = ?",
                new Object[]{chatId},
                (resultSet, rowNum) -> UserEntity.builder()
                        .name(resultSet.getString("name"))
                        .firstName(resultSet.getString("first_name"))
                        .secondName(resultSet.getString("second_name"))
                        .chatId(resultSet.getLong("chat_id"))
                        .phone(resultSet.getString("phone"))
                        .birthday(resultSet.getDate("birthday"))
                        .build()
        ).stream().findFirst();
    }

    @Override
    public Optional<UserEntity> getUserByPhone(String phone) {
        return jdbcTemplate.query(
                "select * from USERS_TEMP where phone = ?",
                new Object[]{phone},
                (resultSet, rowNum) -> UserEntity.builder()
                        .name(resultSet.getString("name"))
                        .firstName(resultSet.getString("first_name"))
                        .secondName(resultSet.getString("second_name"))
                        .chatId(resultSet.getLong("chat_id"))
                        .phone(resultSet.getString("phone"))
                        .birthday(resultSet.getDate("birthday"))
                        .build()
        ).stream().findFirst();
    }

    @Override
    public List<UserEntity> getUsers() {
        return jdbcTemplate.query(
                "select * from USERS_TEMP",
                (resultSet, rowNum) -> UserEntity.builder()
                        .name(resultSet.getString("name"))
                        .firstName(resultSet.getString("first_name"))
                        .secondName(resultSet.getString("second_name"))
                        .chatId(resultSet.getLong("chat_id"))
                        .phone(resultSet.getString("phone"))
                        .birthday(resultSet.getDate("birthday"))
                        .build()
        );
    }

    @Override
    public void delete(long chatId) {
        jdbcTemplate.update(
                "delete FROM USERS_TEMP where chat_id = ?",
                chatId
        );
    }
}
