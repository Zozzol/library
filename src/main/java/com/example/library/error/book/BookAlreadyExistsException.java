package com.example.library.error.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookAlreadyExistsException extends RuntimeException {

    public BookAlreadyExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException create(int id) {
        BookAlreadyExistsException exception = new BookAlreadyExistsException(String.format("Book with ID %d already exists.", id));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
