package com.example.library.error;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExists extends RuntimeException {

    private UserAlreadyExists(String message) {
        super(message);
    }

    public static ResponseStatusException create(String username) {
        UserAlreadyExists exception = new UserAlreadyExists(String.format("User with username: %s already exists.", username));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
