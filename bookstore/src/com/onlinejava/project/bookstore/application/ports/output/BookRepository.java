package com.onlinejava.project.bookstore.application.ports.output;

import com.onlinejava.project.bookstore.application.domain.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    void save();

    Optional<Book> findByTitle(String title);
}
