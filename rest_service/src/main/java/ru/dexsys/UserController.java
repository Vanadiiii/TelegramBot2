package ru.dexsys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.dexsys.domain.entity.Birthday;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.domain.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/telegram-bot/api")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public HttpEntity<List<UserEntity>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/users/{id}")
    public HttpEntity<UserEntity> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userService.getUser(id)
                        .orElseThrow(() -> NoSuchUserException.init(id))
        );
    }

    @DeleteMapping("/users/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Long id) {
        userService.removeUser(id);
        return ResponseEntity.EMPTY;
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<UserEntity> addUser(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(userService.save(userEntity));
    }

    @PatchMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> changeUser(@RequestBody UserEntity userEntity, @PathVariable Long id) {
        userService.removeUser(id);
        return ResponseEntity.ok(userService.save(userEntity));
    }

    @PutMapping("/users/{id}")
    public HttpEntity<?> changeUserValues(
            @RequestHeader(required = false) Integer day,
            @RequestHeader(required = false) Integer month,
            @PathVariable Long id
    ) {
        Optional<UserEntity> user = userService.getUser(id);
        if (day != null) {
            user.ifPresent(userEntity ->
                    Optional.ofNullable(userEntity.getBirthday())
                            .ifPresentOrElse(
                                    birthday -> birthday.setDay(day),
                                    () -> userEntity.setBirthday(new Birthday(day, null))
                            )
            );
        }
        if (month != null) {
            user.ifPresent(userEntity ->
                    Optional.ofNullable(userEntity.getBirthday())
                            .ifPresentOrElse(
                                    birthday -> birthday.setMonth(month),
                                    () -> userEntity.setBirthday(new Birthday(null, month))
                            )
            );
        }
        return ResponseEntity.EMPTY;
    }
}
