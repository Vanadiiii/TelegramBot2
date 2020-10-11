package ru.dexsys.domain.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dexsys.domain.UserDataGateway;
import ru.dexsys.domain.entity.User;

import java.util.List;
import java.util.Optional;

@Data
@Slf4j
@Component
public class UserService {
    private final UserDataGateway userDataGateway;

    public User save(User user) {
        return userDataGateway.save(user);
    }

    public List<User> getUsers() {
        return userDataGateway.findAll();
    }

    public Optional<User> getUser(long id) {
        return userDataGateway.findById(id);
    }

    public void updateUser(User user) {
        userDataGateway.update(user.getId(), user.getBirthday().getDay(), user.getBirthday().getMonth());
    }

    public void removeUser(User user) {
        userDataGateway.delete(user);
    }
}
