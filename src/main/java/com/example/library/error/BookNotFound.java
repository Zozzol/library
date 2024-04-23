package com.example.library.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFound extends RuntimeException {

    private BookNotFound(String message) {
        super(message);
    }

    public static ResponseStatusException create(int id) {
        BookNotFound exception = new BookNotFound(String.format("Book with ID %d not found.", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
