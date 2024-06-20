package com.example.library.controller;

import com.example.library.dto.book.BookRequestDto;
import com.example.library.dto.book.BookResponseDto;
import com.example.library.error.book.BookAlreadyExistsException;
import com.example.library.error.book.BookNotFoundException;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> addBook(@RequestBody BookRequestDto bookRequestDto) {
        return bookService.addBook(bookRequestDto);
    }

    @DeleteMapping("/delete/{bookId}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> deleteBook(@PathVariable Integer bookId) {
        return bookService.deleteBook(bookId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN','ROLE_READER')")
    public Iterable<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/getBook/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN, ROLE_READER')")
    public @ResponseBody Optional<BookResponseDto> getBook(@PathVariable Integer bookId) {
        return bookService.getBook(bookId);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookDoesNotExistsException(BookNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<String> handleBookAlreadyExistsException(BookAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
