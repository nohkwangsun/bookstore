package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.domain.exception.DuplicateItemException;
import com.onlinejava.project.bookstore.application.domain.exception.NoSuchItemException;
import com.onlinejava.project.bookstore.application.ports.input.BookUseCase;
import com.onlinejava.project.bookstore.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;

import java.util.List;

import static com.onlinejava.project.bookstore.application.domain.exception.TooManyItemsException.Term.BookTitle;
import static com.onlinejava.project.bookstore.application.domain.exception.UnexpectedException.throwIfFailed;
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

    @Override
    public void deleteBook(String title) {
        Book book = getBookByTitle(title);
        boolean result = repository.remove(book);
        throwIfFailed(result, "failed to remove the book titled : " + title);
    }

    private Book getBookByTitle(String title) {
        return repository.findByTitle(title)
                .orElseThrow(NoSuchItemException.supplierOf(BookTitle, title));
    }

    @Override
    public void createBook(Book book) {
        repository.findByTitle(book.getTitle())
                .ifPresent(DuplicateItemException.consumerOf(BookTitle, book.getTitle()));
        boolean result = repository.add(book);
        throwIfFailed(result, "failed to add new book" );
    }

    @Override
    public List<Book> searchBook(String keyword) {
        List<Book> books = repository.findAll().stream()
                .filter(book -> book.getTitle().contains(keyword))
                .toList();
        throwIfEmptyBook(books);
        return books;
    }

    @Override
    public void addStock(String title, int stock) {
        Book book = getBookByTitle(title);
        book.addStock(stock);
    }

    private void throwIfEmptyBook(List<Book> books) {
        throwIfEmpty(books, Book.class);
    }
}
