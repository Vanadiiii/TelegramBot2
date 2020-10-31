package ru.dexsys.domain.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dexsys.domain.UserDataGateway;
import ru.dexsys.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Data
@Slf4j
@Component
public class UserService {
    private final UserDataGateway userDataGateway;

    public UserEntity save(UserEntity userEntity) {
        return userDataGateway.save(userEntity);
    }

    public List<UserEntity> getUsers() {
        return userDataGateway.getUsers();
    }

    public Optional<UserEntity> getUser(long id) {
        return userDataGateway.getUserById(id);
    }

    public void updateDay(long userid, int day) {
        userDataGateway.updateDay(userid, day);
    }

    public void updateMonth(long userId, int month) {
        userDataGateway.updateMonth(userId, month);
    }

    public void updatePhone(long userId, String phone) {
        userDataGateway.updatePhone(userId, phone);
    }

    public void removeUser(long id) {
        userDataGateway.delete(id);
    }

    public boolean hasUser(UserEntity user) {
        return userDataGateway.getUserById(user.getId()).isPresent();
    }

    public boolean hasUser(long userId) {
        return userDataGateway.getUserById(userId).isPresent();
    }
}
