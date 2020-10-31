package ru.dexsys.rest_service.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException() {
    }

    public NoSuchUserException(String message) {
        super(message);
    }

    public static NoSuchUserException init(Long id) {
        return new NoSuchUserException("There are no such user with id - " + id);
    }
}
