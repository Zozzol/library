package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(code=HttpStatus.CREATED)
    public @ResponseBody Book addBook(@RequestBody Book book){
        return bookService.create(book);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code=HttpStatus.GONE)
    public @ResponseBody void deleteBook(@PathVariable int id){
        bookService.delete(id);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<Book> getAllBooks(){
        return bookService.getAll();
    }

    @GetMapping("/getOne/{id}")
    public @ResponseBody Book getOne(@PathVariable int id) {
        return bookService.getOne(id);
    }
}
