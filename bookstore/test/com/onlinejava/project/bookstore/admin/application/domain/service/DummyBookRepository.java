package com.onlinejava.project.bookstore.admin.application.domain.service;

import com.onlinejava.project.bookstore.common.domain.entity.Book;
import com.onlinejava.project.bookstore.admin.application.ports.output.BookRepository;

import java.util.List;
import java.util.Optional;

public class DummyBookRepository implements BookRepository {
    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public boolean remove(Book book) {
        return false;
    }

    @Override
    public boolean add(Book book) {
        return false;
    }
}
