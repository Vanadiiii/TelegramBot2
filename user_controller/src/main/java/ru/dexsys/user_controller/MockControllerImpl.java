package ru.dexsys.user_controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.dexsys.domain.UserDataGateway;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.user_controller.dto.UserDto2;
import ru.dexsys.user_controller.mapper.UserDto2EntityMapper;
import ru.dexsys.user_controller.mapper.UserEntity2DtoMapper;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
@ConditionalOnProperty(value = "repository.type", havingValue = "mock")
@PropertySource("classpath:mock.properties")
public class MockControllerImpl implements UserDataGateway {
    private final RestOperations restTemplate;
    private final UserDto2EntityMapper userDto2EntityMapper;
    private final UserEntity2DtoMapper userEntity2DtoMapper;

    public MockControllerImpl(
            UserDto2EntityMapper userDto2EntityMapper,
            UserEntity2DtoMapper userEntity2DtoMapper
    ) {
        this.restTemplate = new RestTemplate();
        this.userDto2EntityMapper = userDto2EntityMapper;
        this.userEntity2DtoMapper = userEntity2DtoMapper;
    }

    @Value("${baseUri}")
    private String baseUri;

    @Override
    public UserEntity save(UserEntity userEntity) {
        var user = userEntity2DtoMapper.apply(userEntity);
        UUID generatedUUID;
        try {
            generatedUUID = UUID.fromString(
                    Objects.requireNonNull(
                            restTemplate.postForEntity(baseUri, user, String.class)
                                    .getBody()
                    )
            );
        } catch (Exception e) {
            log.error("exception with saving user #" + userEntity.getId());
            log.error("User -> " + userEntity);
            throw new RuntimeException(e);
        }
        log.info("generated UUID in mock-controller is '" + generatedUUID + '\'');
        return userEntity;
    }

    @Override
    public List<UserEntity> getUsers() {
        return Arrays.stream(
                Objects.requireNonNull(
                        restTemplate
                                .getForEntity(baseUri, UserDto2[].class)
                                .getBody()
                ))
                .map(userDto2EntityMapper::apply)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserEntity> getUserById(long id) {
        return getUsers().stream()
                .filter(userEntity -> id == userEntity.getId())
                .findFirst();
    }

    @Override
    public Optional<UserEntity> getUserByPersonalData_Phone(String phone) {
        return getUsers().stream()
                .filter(userEntity -> phone.equals(userEntity.getPhone()))
                .findFirst();
    }

    @Override
    public void updateDay(Long id, int day) {
        TODO("updateDay");
    }

    @Override
    public void updateMonth(Long id, int day) {
        TODO("updateMonth");
    }

    @Override
    public void updatePhone(long userId, String phone) {
        TODO("updatePhone");

    }

    @Override
    public void delete(Long id) {
        TODO("delete");
    }

    private void TODO(String methodName) {
        log.error("trying to use not implemented/created method - '{}'", methodName);
        throw new RuntimeException("NOT IMPLEMENTED/CREATED METHOD");
    }
}
