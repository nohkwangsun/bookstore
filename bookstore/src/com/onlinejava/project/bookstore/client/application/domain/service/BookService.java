package com.onlinejava.project.bookstore.client.application.domain.service;

import com.onlinejava.project.bookstore.client.application.domain.exception.NoSuchItemException;
import com.onlinejava.project.bookstore.client.application.ports.input.BookUseCase;
import com.onlinejava.project.bookstore.client.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.common.domain.entity.Book;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;

import java.util.List;

import static com.onlinejava.project.bookstore.common.domain.exception.TooManyItemsException.Term.BookTitle;
import static com.onlinejava.project.bookstore.core.util.validator.Validators.throwIfEmpty;

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
        List<Book> list = repository.findAll();
        throwIfEmptyBook(list);
        return list;
    }

    private Book getBookByTitle(String title) {
        return repository.findByTitle(title)
                .orElseThrow(NoSuchItemException.supplierOf(BookTitle, title));
    }

    @Override
    public List<Book> searchBook(String keyword) {
        List<Book> books = repository.findAll().stream()
                .filter(book -> book.getTitle().contains(keyword))
                .toList();
        throwIfEmptyBook(books);
        return books;
    }

    private void throwIfEmptyBook(List<Book> books) {
        throwIfEmpty(books, Book.class);
    }
}
