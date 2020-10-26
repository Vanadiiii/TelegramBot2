package ru.dexsys.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.domain.service.UserService;
import ru.dexsys.dto.UserDto;
import ru.dexsys.exception.NoSuchUserException;
import ru.dexsys.mapper.UserDataDtoMapper;
import ru.dexsys.mapper.UserDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;
    private final UserDataDtoMapper userDataDtoMapper;

    @GetMapping("/users")
    public HttpEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(
                userService.getUsers()
                        .stream()
                        .map(userDtoMapper)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/users/{id}")
    public HttpEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userService.getUser(id)
                        .map(userDtoMapper)
                        .orElseThrow(() -> NoSuchUserException.init(id))
        );
    }

    @DeleteMapping("/users/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Long id) {
        userService.removeUser(id);
        return ResponseEntity.ok("User #" + id + " was removed from storage");
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        userService.save(userDataDtoMapper.apply(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<UserDto> changeUser(
            @RequestBody UserEntity userEntity,
            @PathVariable Long id
    ) {
        userService.removeUser(id);
        return ResponseEntity.ok(userDtoMapper.apply(userService.save(userEntity)));
    }

    @PutMapping("/users/{id}")
    public HttpEntity<UserDto> changeUserValues(
            @RequestHeader(required = false) Integer day,
            @RequestHeader(required = false) Integer month,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                userService.getUser(id)
                        .map(user -> user.setDay(day))
                        .map(user -> user.setMonth(month))
                        .map(userDtoMapper)
                        .orElseThrow(() -> NoSuchUserException.init(id))
        );
    }
}
