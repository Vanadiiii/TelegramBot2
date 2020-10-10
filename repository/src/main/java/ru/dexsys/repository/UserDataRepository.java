package ru.dexsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dexsys.domain.UserDataGateway;
import ru.dexsys.domain.entity.User;

@Repository
@Transactional
public interface UserDataRepository extends UserDataGateway, JpaRepository<User, Long> {
    @Query("update USERS U set U.day = ?2, U.month = ?3 where U.id = ?1")
    @Modifying
    void update(Long id, int day, int month);
}
