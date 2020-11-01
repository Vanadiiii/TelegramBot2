package ru.dexsys.rest_service.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException init(Long id) {
        return new UserNotFoundException("There are no such user with id - " + id);
    }
}
