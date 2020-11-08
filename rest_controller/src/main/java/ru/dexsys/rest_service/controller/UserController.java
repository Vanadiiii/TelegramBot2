package ru.dexsys.rest_service.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dexsys.domain.IUserDomainService;
import ru.dexsys.rest_service.dto.UserDto;
import ru.dexsys.rest_service.exception.UserNotFoundException;
import ru.dexsys.rest_service.mapper.UserDataDtoMapper;
import ru.dexsys.rest_service.mapper.UserDtoMapper;
import ru.dexsys.rest_service.randomizer.UserDtoRandomizer;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final IUserDomainService userService;
    private final UserDtoMapper userDtoMapper;
    private final UserDataDtoMapper userDataDtoMapper;
    private final UserDtoRandomizer randomizer;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(
                userService.getUsers()
                        .stream()
                        .map(userDtoMapper)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/users/{chatId}")
    public ResponseEntity<?> getUserByChatId(@PathVariable Long chatId) {
        return ResponseEntity.ok(
                userService.getUser(chatId)
                        .map(userDtoMapper)
                        .orElseThrow(() -> UserNotFoundException.init(chatId))
        );
    }

    @GetMapping("/users/findBy")
    public ResponseEntity<?> getUserByPhone(@NonNull @RequestHeader String phone) {
        return ResponseEntity.ok(
                userService.getUserByPhone(phone)
                        .map(userDtoMapper)
                        .orElseThrow(() -> UserNotFoundException.init(phone))
        );
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        userService.saveToTemp(userDataDtoMapper.apply(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(
            @RequestBody UserDto userDto
    ) {
        try {
            userService.removeFromTemp(userDto.getChatId());
            userService.saveToTemp(userDataDtoMapper.apply(userDto));
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            throw UserNotFoundException.init(userDto.getChatId());
        }
    }

    @PutMapping("/users/{chatId}")
    public ResponseEntity<UserDto> updateUserValues(
            @RequestHeader(required = false) Date birthday,
            @RequestHeader(required = false) String phone,
            @PathVariable Long chatId
    ) {
        try {
            if (birthday != null) {
                userService.updateBirthday(chatId, birthday);
            }
            if (phone != null) {
                userService.updatePhone(chatId, phone);
            }
            UserDto userDto = userService.getUser(chatId)
                    .map(userDtoMapper)
                    .orElseThrow(() -> UserNotFoundException.init(chatId));
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            throw UserNotFoundException.init(chatId);
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> generate() {
        UserDto userDto = randomizer.randomize();
        return createUser(userDto);
    }
}
