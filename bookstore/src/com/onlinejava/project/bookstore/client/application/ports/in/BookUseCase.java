package com.onlinejava.project.bookstore.client.application.ports.in;

import com.onlinejava.project.bookstore.common.domain.entity.Book;

import java.util.List;

public interface BookUseCase {
    List<Book> getBookList();

    List<Book> searchBook(String keyword);
}
