package com.example.library.error.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
