package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.BookStoreFactory;
import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.ports.input.BookUseCase;
import com.onlinejava.project.bookstore.application.ports.output.BookRepository;

import java.util.List;
import java.util.stream.IntStream;

public class BookService implements BookUseCase {

    private BookRepository repository;

    public BookService() {
    }

    public void setDependency() {
        this.repository = BookStoreFactory.lookup(BookRepository.class);
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
    public void addStock(String titleToAddStock, int stock) {
        getBookList().stream()
                .filter(book -> book.getTitle().equals(titleToAddStock))
                .forEach(book -> book.addStock(stock));
    }

    @Override
    public void printBookList(List<Book> bookList) {
        printTableLine();
        printHeader();
        bookList.forEach(this::printTable);
        printTableLine();
    }

    private void printHeader() {
        printTableLine();
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |\n",
                "TITLE", "WRITER", "PRICE", "LOCATION", "STOCK");
    }

    private void printTable(Book book) {
        printTableLine();
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |\n",
                book.getTitle(), book.getWriter(), book.getPrice(), book.getLocation(), book.getStock());
    }

    private void printTableLine() {
        IntStream.range(1, 60).forEach(i -> System.out.print("-"));
        System.out.println();
    }
}
