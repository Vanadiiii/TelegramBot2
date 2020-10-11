package ru.dexsys.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dexsys.domain.entity.Birthday;
import ru.dexsys.domain.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserDataGatewayStab implements UserDataGateway {
    private final List<User> testStorage = new ArrayList<>();

    @Override
    public void update(Long id, int day, int month) {
        Birthday birthday = new Birthday(day, month);
        findById(id)
                .orElseThrow()
                .setBirthday(birthday);
    }

    @Override
    public User save(User user) {
        testStorage.add(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return testStorage;
    }

    @Override
    public Optional<User> findById(long id) {
        return testStorage
                .stream()
                .filter(user -> id == user.getId())
                .findFirst();
    }

    @Override
    public void delete(User user) {
        testStorage.remove(user);
    }
}
