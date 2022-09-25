package com.onlinejava.project.bookstore.application.ports.input;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;

import java.util.List;

public interface PurchaseUseCase {
    void buyBook(String titleToBuy, String customer);

    int getPoint(Book book, String customer);

    List<Purchase> getPurchaseList();

    List<Purchase> getPurchaseListByUser(String userName);
}
