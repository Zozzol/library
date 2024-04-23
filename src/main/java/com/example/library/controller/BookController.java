package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(code=HttpStatus.CREATED)
    public @ResponseBody Book addBook(@RequestBody Book book){
        return bookService.create(book);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(code=HttpStatus.GONE)
    public @ResponseBody void deleteBook(@PathVariable int id){
        bookService.delete(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN') or hasRole('READER')")
    public @ResponseBody Iterable<Book> getAllBooks(){
        return bookService.getAll();
    }

    @GetMapping("/getOne/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('READER')")
    public @ResponseBody Book getOne(@PathVariable int id) {
        return bookService.getOne(id);
    }
}
