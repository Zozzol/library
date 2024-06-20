package com.example.library.error.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }

    public static ResponseStatusException create(int id) {
        BookNotFoundException exception = new BookNotFoundException(String.format("Book with ID %d not found.", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
