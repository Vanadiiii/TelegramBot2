package ru.dexsys.repository;

import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.dexsys.domain.IUserDataGateway;
import ru.dexsys.domain.entity.UserEntity;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
@Slf4j
@ConditionalOnProperty(value = "repository.type", havingValue = "stab")
public class UserDataGatewayStab implements IUserDataGateway {
    private final Map<Long, UserEntity> storage = new HashMap<>();

    @PostConstruct
    private void initStorage() {
        storage.put(
                -1L,
                UserEntity.builder()
                        .firstName("Иван")
                        .secondName("Матвеев")
                        .phone("89127684213")
                        .build()
        );
    }

    @Override
    public List<UserEntity> getUsers() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<UserEntity> getUserByChatId(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<UserEntity> getUserByPhone(String phone) {
        return storage.values()
                .stream()
                .filter(user -> phone.equals(user.getPhone()))
                .findFirst();
    }

    @Override
    public void updatePhone(long id, String phone) {
        Optional.ofNullable(storage.get(id))
                .ifPresentOrElse(
                        user -> user.setPhone(phone),
                        () -> {
                            log.error("There are no user #" + id + " in repository");
                            throw new RuntimeException("There are no user #" + id + " in repository");
                        }
                );
    }

    @Override
    public void updateBirthday(Long id, Date birthday) {
        Optional.ofNullable(storage.get(id))
                .ifPresentOrElse(
                        user -> user.setBirthday(birthday),
                        () -> {
                            log.error("There are no user #" + id + " in repository");
                            throw new RuntimeException("There are no user #" + id + " in repository");
                        }
                );
    }

    @Override
    public void updateChatId(String phone, long chatId) {
        getUserByPhone(phone)
                .ifPresentOrElse(
                        user -> user.setChatId(chatId),
                        () -> {
                            log.error("There are no user with phone '" + phone + "' in repository");
                            throw new RuntimeException("There are no user with phone '" + phone + "' in repository");
                        }
                );
    }

    @Override
    public UserEntity generate() {// TODO: 4/11/20 change ERParams !
        EasyRandom random = new EasyRandom();
        return random.nextObject(UserEntity.class);
    }
}


