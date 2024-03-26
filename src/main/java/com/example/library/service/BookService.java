//package com.example.library.service;
//
//import java.util.Iterator;
//import java.util.List;
//
//import com.example.library.repository.BookRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import com.example.library.entity.Book;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Service;
//
//
//@Service
//  public class BookService {
//
//    private final BookRepository bookRepository;
//
//    @Autowired
//    public BookService(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }
//
//    public Iterable<Book> getAll() {
//        return bookRepository.findAll();
//    }
//
//    public Book getOne(int id) {
//        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
//    }
//
//    public Book create(Book book) {
//        return bookRepository.save(book);
//    }
//}
