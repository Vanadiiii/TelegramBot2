package ru.dexsys.rest_service.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dexsys.domain.service.UserService;
import ru.dexsys.rest_service.dto.UserDto;
import ru.dexsys.rest_service.exception.UserNotFoundException;
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
        return ResponseEntity.ok(
                userService.getUser(id)
                        .map(userDtoMapper)
                        .orElseThrow(() -> UserNotFoundException.init(id))
        );
    }

    @GetMapping("/users/findBy")
    public ResponseEntity<?> getUserByPhone(@NonNull @RequestHeader String phone) {
        return ResponseEntity.ok(
                userService.getUserByPhone(phone)
                        .map(userDtoMapper)
                        .orElseThrow(() -> new UserNotFoundException("There are no such user with phone - " + phone))
        );
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.removeUser(id);
            return ResponseEntity.ok("User #" + id + " was removed from storage");
        } catch (Exception e) {
            throw UserNotFoundException.init(id);
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
        try {
            userService.removeUser(userDto.getId());
            userService.save(userDataDtoMapper.apply(userDto));
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            throw UserNotFoundException.init(userDto.getId());
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> changeUserValues(
            @RequestHeader(required = false) Integer day,
            @RequestHeader(required = false) Integer month,
            @RequestHeader(required = false) String phone,
            @PathVariable Long id
    ) {
        try {
            if (day != null) {
                userService.updateDay(id, day);
            }
            if (month != null) {
                userService.updateMonth(id, month);
            }
            if (phone != null) {
                userService.updatePhone(id, phone);
            }
            UserDto userDto = userService.getUser(id)
                    .map(userDtoMapper)
                    .orElseThrow(() -> UserNotFoundException.init(id));
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            throw UserNotFoundException.init(id);
        }
    }
}
