package com.onlinejava.project.bookstore.client.adapters.socket;

import com.onlinejava.project.bookstore.client.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.common.domain.entity.Book;
import com.onlinejava.project.bookstore.core.factory.Bean;

import java.util.List;
import java.util.Optional;

@Bean
public class SocketBookRepository implements BookRepository {
    @Override
    public List<Book> findAll() {
        return List.of();
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return Optional.empty();
    }
}
