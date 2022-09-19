package com.onlinejava.project.bookstore.domain.entity;

import com.onlinejava.project.bookstore.domain.entity.Book;
import com.onlinejava.project.bookstore.ports.output.BookRepository;

import java.util.List;
import java.util.Optional;

public class BookRepositoryStub implements BookRepository {
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
