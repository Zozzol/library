package com.example.library.service;

import com.example.library.dto.book.BookRequestDto;
import com.example.library.dto.book.BookResponseDto;
import com.example.library.entity.Book;
import com.example.library.error.book.BookAlreadyExistsException;
import com.example.library.error.book.BookNotFoundException;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ResponseEntity<String> addBook(BookRequestDto bookRequestDto) {
        Optional<Book> existingBook = bookRepository.findById(bookRequestDto.getId());
        if (existingBook.isPresent()) {
            throw BookAlreadyExistsException.create(bookRequestDto.getId());
        } else {
            Book book = new Book();
            book.setId(bookRequestDto.getId());
            book.setIsbn(bookRequestDto.getIsbn());
            book.setTitle(bookRequestDto.getTitle());
            book.setAuthor(bookRequestDto.getAuthor());
            book.setPublisher(bookRequestDto.getPublisher());
            book.setPublishYear(bookRequestDto.getPublishYear());
            book.setAvailableCopies(bookRequestDto.getAvailableCopies());
            bookRepository.save(book);
            return new ResponseEntity<>("Book added successfully", HttpStatus.OK);
        }
    }

    public ResponseEntity<String> deleteBook(Integer bookId) {
        Optional<Book> existingBook = bookRepository.findById(bookId);
        if (existingBook.isEmpty()) {
            throw BookNotFoundException.create(bookId);
        } else {
            bookRepository.deleteById(bookId);
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        }
    }

    public Iterable<BookResponseDto> getAllBooks() {
        Iterable<Book> books = bookRepository.findAll();
        return StreamSupport.stream(books.spliterator(), false)
                .map(book -> new BookResponseDto(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishYear(), book.getPublisher(), book.getAvailableCopies()))
                .collect(Collectors.toList());
    }

    public Optional<BookResponseDto> getBook(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book foundBook = book.get();
            BookResponseDto bookResponseDto = new BookResponseDto(foundBook.getId(), foundBook.getTitle(), foundBook.getAuthor(), foundBook.getIsbn(), foundBook.getPublishYear(), foundBook.getPublisher(), foundBook.getAvailableCopies());
            return Optional.of(bookResponseDto);
        } else {
            throw BookNotFoundException.create(id);
        }
    }
}
