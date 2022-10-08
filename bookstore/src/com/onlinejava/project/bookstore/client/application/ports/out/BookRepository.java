package com.onlinejava.project.bookstore.client.application.ports.out;

import com.onlinejava.project.bookstore.common.domain.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends Repository {
    List<Book> findAll();

    Optional<Book> findByTitle(String title);
}
