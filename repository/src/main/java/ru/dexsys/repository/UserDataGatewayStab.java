package ru.dexsys.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.dexsys.domain.UserDataGateway;
import ru.dexsys.domain.entity.Birthday;
import ru.dexsys.domain.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@ConditionalOnProperty(value = "repository.type", havingValue = "stab")
public class UserDataGatewayStab implements UserDataGateway {
    private final List<User> testStorage = new ArrayList<>();

    @Override
    public void updateDay(Long id, int day) {
        User user = find(id)
                .orElseThrow(() -> new RuntimeException("There are no user " + id + " in repository"));

        Optional.ofNullable(user.getBirthday())
                .ifPresentOrElse(
                        birthday -> birthday.setDay(day),
                        () -> user.setBirthday(new Birthday(day, null))
                );
    }

    @Override
    public void updateMonth(Long id, int month) {
        User user = find(id)
                .orElseThrow(() -> new RuntimeException("There are no user " + id + " in repository"));

        Optional.ofNullable(user.getBirthday())
                .ifPresentOrElse(
                        birthday -> birthday.setMonth(month),
                        () -> user.setBirthday(new Birthday(null, month))
                );
    }

    @Override
    public User save(User user) {
        testStorage.add(user);
        return user;
    }

    @Override
    public List<User> find() {
        return testStorage;
    }

    @Override
    public Optional<User> find(long id) {
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


