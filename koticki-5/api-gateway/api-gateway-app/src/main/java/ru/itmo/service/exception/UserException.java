package ru.itmo.service.exception;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
