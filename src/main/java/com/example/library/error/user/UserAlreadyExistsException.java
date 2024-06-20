package com.example.library.error.user;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RuntimeException {

    private UserAlreadyExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String username) {
        UserAlreadyExistsException exception = new UserAlreadyExistsException(String.format("User with username: %s already exists.", username));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
