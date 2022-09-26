package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.ports.output.BookRepository;

import java.util.List;
import java.util.Optional;

public class DummyBookRepository implements BookRepository {
    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return Optional.empty();
    }
}
