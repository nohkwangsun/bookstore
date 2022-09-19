package com.onlinejava.project.bookstore.ports.output;

import com.onlinejava.project.bookstore.domain.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    void save();

    Optional<Book> findByTitle(String title);
}
