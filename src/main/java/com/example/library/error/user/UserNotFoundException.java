package com.example.library.error.user;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {

    private UserNotFoundException(String message) {
        super(message);
    }

    public static ResponseStatusException create(Integer userId) {
        UserNotFoundException exception = new UserNotFoundException(String.format("User with ID %d not found.", userId));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
