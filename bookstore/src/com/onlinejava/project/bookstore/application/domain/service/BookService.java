package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.ports.input.BookUseCase;
import com.onlinejava.project.bookstore.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;

import java.util.List;

@Bean
public class BookService implements BookUseCase {

    @Inject
    private BookRepository repository;

    public BookService() {
    }

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> getBookList() {
        return repository.findAll();
    }

    @Override
    public void deleteBook(String deletingTitle) {
        getBookList().stream()
                .filter(book -> book.getTitle().equals(deletingTitle))
                .findFirst()
                .ifPresent(getBookList()::remove);
    }

    @Override
    public void createBook(Book newBook) {
        getBookList().add(newBook);
    }

    @Override
    public List<Book> searchBook(String keyword) {
        return getBookList().stream()
                .filter(book -> book.getTitle().contains(keyword))
                .toList();
    }

    @Override
    public void addStock(String title, int stock) {
        getBookList().stream()
                .filter(book -> book.getTitle().equals(title))
                .forEach(book -> book.addStock(stock));
    }
}
