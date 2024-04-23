package com.example.library.service;

import com.example.library.error.BookNotFound;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.library.entity.Book;
import org.springframework.stereotype.Service;


@Service
  public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getOne(int id) {
        return bookRepository.findById(id).orElseThrow(() -> BookNotFound.create(id));
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public void delete(int id){
       bookRepository.deleteById(id);
    }
}
