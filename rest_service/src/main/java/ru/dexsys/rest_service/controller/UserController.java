package ru.dexsys.rest_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dexsys.domain.service.UserService;
import ru.dexsys.rest_service.dto.UserDto;
import ru.dexsys.rest_service.exception.NoSuchUserException;
import ru.dexsys.rest_service.mapper.UserDataDtoMapper;
import ru.dexsys.rest_service.mapper.UserDtoMapper;

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
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(
                userService.getUsers()
                        .stream()
                        .map(userDtoMapper)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        if (userService.hasUser(id)) {
            return ResponseEntity.ok(
                    userService.getUser(id)
                            .map(userDtoMapper)
                            .orElseThrow(() -> NoSuchUserException.init(id))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User #" + id + " not found");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (userService.hasUser(id)) {
            userService.removeUser(id);
            return ResponseEntity.ok("User #" + id + " was removed from storage");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User #" + id + " not found");
        }
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        userService.save(userDataDtoMapper.apply(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeUser(
            @RequestBody UserDto userDto
    ) {
        if (userService.hasUser(userDto.getId())) {
            userService.removeUser(userDto.getId());
            userService.save(userDataDtoMapper.apply(userDto));
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User #" + userDto.getId() + " not found");
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> changeUserValues(
            @RequestHeader(required = false) Integer day,
            @RequestHeader(required = false) Integer month,
            @PathVariable Long id
    ) {
        if (day != null) {
            userService.updateDay(id, day);
        }
        if (month != null) {
            userService.updateMonth(id, month);
        }
        UserDto userDto = userService.getUser(id)
                .map(userDtoMapper)
                .orElseThrow(() -> NoSuchUserException.init(id));
        return ResponseEntity.ok(userDto);
    }
}
