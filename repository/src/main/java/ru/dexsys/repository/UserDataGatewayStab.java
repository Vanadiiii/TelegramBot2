package ru.dexsys.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.dexsys.domain.UserDataGateway;
import ru.dexsys.domain.entity.Birthday;
import ru.dexsys.domain.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@ConditionalOnProperty(value = "repository.type", havingValue = "stab")
public class UserDataGatewayStab implements UserDataGateway {
    private final List<UserEntity> testStorage = new ArrayList<>();

    @Override
    public void updateDay(Long id, int day) {
        UserEntity userEntity = find(id)
                .orElseThrow(() -> new RuntimeException("There are no user " + id + " in repository"));

        Optional.ofNullable(userEntity.getBirthday())
                .ifPresentOrElse(
                        birthday -> birthday.setDay(day),
                        () -> userEntity.setBirthday(new Birthday(day, null))
                );
    }

    @Override
    public void updateMonth(Long id, int month) {
        UserEntity userEntity = find(id)
                .orElseThrow(() -> new RuntimeException("There are no user " + id + " in repository"));

        Optional.ofNullable(userEntity.getBirthday())
                .ifPresentOrElse(
                        birthday -> birthday.setMonth(month),
                        () -> userEntity.setBirthday(new Birthday(null, month))
                );
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        testStorage.add(userEntity);
        return userEntity;
    }

    @Override
    public List<UserEntity> find() {
        return testStorage;
    }

    @Override
    public Optional<UserEntity> find(long id) {
        return testStorage
                .stream()
                .filter(user -> id == user.getId())
                .findFirst();
    }

    @Override
    public void delete(UserEntity userEntity) {
        testStorage.remove(userEntity);
    }
}


