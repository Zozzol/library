package com.example.library.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameNotFound extends RuntimeException {

    private UsernameNotFound(String message) {
        super(message);
    }

    public static ResponseStatusException create(String username) {
        UsernameNotFound exception = new UsernameNotFound(String.format("Username %s not found.", username));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
