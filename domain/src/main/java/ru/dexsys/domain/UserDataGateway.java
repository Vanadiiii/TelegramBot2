package ru.dexsys.domain;

import ru.dexsys.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDataGateway {

    void updateDay(Long id, int day);

    void updateMonth(Long id, int day);

    User save(User user);

    List<User> findAll();

    Optional<User> findById(long id);

    void delete(User user);
}
