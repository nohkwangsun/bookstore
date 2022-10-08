package com.onlinejava.project.bookstore.application.ports.output;

import com.onlinejava.project.bookstore.application.domain.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends Repository {
    List<Book> findAll();

    Optional<Book> findByTitle(String title);

    boolean remove(Book book);

    boolean add(Book book);
}
