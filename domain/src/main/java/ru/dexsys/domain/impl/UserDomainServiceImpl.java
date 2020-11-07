package ru.dexsys.domain.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dexsys.domain.IUserDataGateway;
import ru.dexsys.domain.IUserDomainService;
import ru.dexsys.domain.IUserTempDataGateway;
import ru.dexsys.domain.entity.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Data
@Slf4j
@Service
public class UserDomainServiceImpl implements IUserDomainService {
    private final IUserDataGateway userDataGateway;
    private final IUserTempDataGateway userTempDataGateway;

    public UserEntity saveToTemp(UserEntity userEntity) {
        if (userTempDataGateway.getUserByChatId(userEntity.getChatId()).isEmpty()) {
            UserEntity user = userTempDataGateway.save(userEntity);
            log.info("User #{} was saved into temp_storage", user.getChatId());
            return user;
        }
        return userEntity;
    }

    /**
     * <p>just print all (saved and unsaved) users</p>
     */
    public List<UserEntity> getUsers() {
        List<UserEntity> users = userDataGateway.getUsers();
        users.addAll(userTempDataGateway.getUsers());
        return users;
    }

    /**
     * <p2>find user from permanent storage only</p2>
     */
    public Optional<UserEntity> getUser(long id) {
        if (userDataGateway.getUserByChatId(id).isPresent()){
            return userDataGateway.getUserByChatId(id);
        } else return userTempDataGateway.getUserByChatId(id);
    }

    public Optional<UserEntity> getUserByPhone(String phone) {
        return userDataGateway.getUserByPhone(phone);
    }

    public void updateBirthday(Long userId, Date birthday) {
        userDataGateway.updateBirthday(userId, birthday);
    }

    public void updatePhone(long userId, String phone) {
        userDataGateway.updatePhone(userId, phone);
    }

    @Override
    public void updateChatId(String phone, long chatId) {
        userDataGateway.updateChatId(phone, chatId);
    }

    public void removeFromTemp(long id) {
        userTempDataGateway.delete(id);
    }

    public boolean hasUser(long userId) {
        return userDataGateway.getUserByChatId(userId).isPresent();
    }

    public boolean hasUser(String phone) {
        return userDataGateway.getUserByPhone(phone).isPresent();
    }
}
