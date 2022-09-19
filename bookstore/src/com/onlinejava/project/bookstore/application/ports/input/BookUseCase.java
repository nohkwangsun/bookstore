package com.onlinejava.project.bookstore.application.ports.input;

import com.onlinejava.project.bookstore.application.domain.entity.Book;

import java.util.List;

public interface BookUseCase {
    List<Book> getBookList();

    void deleteBook(String deletingTitle);

    void createBook(Book newBook);

    List<Book> searchBook(String keyword);

    void addStock(String titleToAddStock, int stock);

    void printBookList(List<Book> bookList);
}
