package ru.dexsys.domain;

import org.springframework.stereotype.Component;
import ru.dexsys.domain.entity.User;

import java.util.List;
import java.util.Optional;

@Component
public interface UserDataGateway {

    void updateDay(Long id, int day);

    void updateMonth(Long id, int day);

    User save(User user);

    List<User> find();

    Optional<User> find(long id);

    void delete(User user);
}
