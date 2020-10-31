package ru.dexsys.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.dexsys.domain.UserDataGateway;
import ru.dexsys.domain.entity.Birthday;
import ru.dexsys.domain.entity.UserEntity;

import java.util.*;

@Repository
@Slf4j
@ConditionalOnProperty(value = "repository.type", havingValue = "stab")
public class UserDataGatewayStab implements UserDataGateway {
    private final Map<Long, UserEntity> storage = new HashMap<>();

    @Override
    public void updateDay(Long id, int day) {
        if (storage.containsKey(id)) {
            UserEntity user = storage.get(id);
            Optional.ofNullable(user.getBirthday())
                    .ifPresentOrElse(
                            birthday -> birthday.setDay(day),
                            () -> user.setBirthday(new Birthday(day, null))
                    );
        } else {
            log.error("There are no user #" + id + " in repository for saving day - " + day);
            throw new RuntimeException("There are no user #" + id + " in repository");
        }
    }

    @Override
    public void updateMonth(Long id, int month) {
        if (storage.containsKey(id)) {
            UserEntity user = storage.get(id);
            Optional.ofNullable(user.getBirthday())
                    .ifPresentOrElse(
                            birthday -> birthday.setMonth(month),
                            () -> user.setBirthday(new Birthday(null, month))
                    );
        } else {
            log.error("There are no user #" + id + " in repository while saving month - " + month);
            throw new RuntimeException("There are no user #" + id + " in repository while saving month - " + month);
        }
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        storage.put(userEntity.getId(), userEntity);
        return userEntity;
    }

    @Override
    public List<UserEntity> getUsers() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<UserEntity> getUserById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void delete(Long id) {
        if (storage.containsKey(id)) {
            UserEntity user = storage.get(id);
            storage.remove(id, user);
        }
    }

    @Override
    public void updatePhone(long id, String phone) {
        if (storage.containsKey(id)) {
            storage.get(id).setPhone(phone);
        } else {
            log.error("There are no user #" + id + " in repository for saving phone - " + phone);
            throw new RuntimeException("There are no user #" + id + " in repository");
        }
    }
}


