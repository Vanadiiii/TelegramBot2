package ru.dexsys.user_controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestOperations;
import ru.dexsys.domain.IUserDataGateway;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.user_controller.dto.UserFromMockDto;
import ru.dexsys.user_controller.dto.UserUpdateDto;
import ru.dexsys.user_controller.mapper.UserFromMock2EntityMapper;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
@ConditionalOnProperty(value = "repository.type", havingValue = "mock")
@PropertySource("classpath:mock.properties")
@RequiredArgsConstructor
public class MockControllerImpl implements IUserDataGateway {
    private final RestOperations restTemplate;
    private final UserFromMock2EntityMapper userFromMock2EntityMapper;

    @Value("${baseUri}")
    private String baseUri;

    @Override
    public List<UserEntity> getUsers() {
        return Arrays.stream(
                Objects.requireNonNull(
                        restTemplate
                                .getForEntity(baseUri, UserFromMockDto[].class)
                                .getBody()
                ))
                .map(userFromMock2EntityMapper::apply)
                .collect(Collectors.toList());
    }

    public List<UserFromMockDto> getUsersWithoutMapping() {
        return Arrays.stream(
                Objects.requireNonNull(
                        restTemplate
                                .getForEntity(baseUri, UserFromMockDto[].class)
                                .getBody()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserEntity> getUserByChatId(long chatId) {
        return getUsers().stream()
                .filter(userEntity -> chatId == userEntity.getChatId())
                .findFirst();
    }

    @Override
    public Optional<UserEntity> getUserByPhone(String phone) {
        return getUsers().stream()
                .filter(userEntity -> phone.equals(userEntity.getPhone()))
                .findFirst();
    }

    @Override
    public void updateChatId(String phone, long chatId) {
        UserFromMockDto user = getUsersWithoutMapping().stream()
                .filter(
                        userDto -> Optional.ofNullable(userDto)
                                .map(UserFromMockDto::getPhone)
                                .map(phn -> phn.replaceAll("\\D", ""))
                                .map(phn -> phn.equals(phone))
                                .orElse(false)
                )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There are no User #" + chatId));
        String ENDPOINT = "/" + user.getId();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                    .chatId(String.valueOf(chatId))
                    .phone(phone)
                    .build();

            HttpEntity<UserUpdateDto> body = new HttpEntity<>(userUpdateDto, headers);

            String response = restTemplate.patchForObject(
                    baseUri + ENDPOINT,
                    body,
                    String.class
            );
            log.info("Mock-server response - " + response);
        } catch (Exception e) {
            log.error("Exception with updating the User #{}", chatId, e);
        }
    }

    @Override
    public void updateBirthday(Long chatId, Date birthday) {
        UserFromMockDto user = getUsersWithoutMapping().stream()
                .filter(userDto -> String.valueOf(chatId).equals(userDto.getChatId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There are no User #" + chatId));
        String ENDPOINT = "/" + user.getId();
        String phone = getUserByChatId(chatId)
                .map(UserEntity::getPhone)
                .orElseThrow(() -> new RuntimeException("There are no such User #" + chatId));
        try {
            String response = restTemplate.patchForObject(
                    baseUri + ENDPOINT,
                    UserUpdateDto.builder()
                            .chatId(String.valueOf(chatId))
                            .phone(phone)
                            .birthDay(birthday)
                            .build(),
                    String.class
            );
            log.info("Mock-server response - " + response);
        } catch (Exception e) {
            log.error("Exception with updating the User #{}", chatId, e);
        }
    }

    @Override
    public UserEntity generate() {
        UserFromMockDto user = restTemplate
                .postForEntity(baseUri, null, UserFromMockDto.class)
                .getBody();
        log.info("generate new User ->  " + user);
        return userFromMock2EntityMapper.apply(user);
    }

    @Override
    public void updatePhone(long userId, String phone) {
        log.error("trying to use not implemented/created method - 'updatePhone'");
        throw new RuntimeException("NOT IMPLEMENTED/CREATED METHOD");
    }
}
