package com.example.library.repository;

import org.springframework.stereotype.Repository;
import com.example.library.entity.Book;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
