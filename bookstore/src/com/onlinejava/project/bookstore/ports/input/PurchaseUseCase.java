package com.onlinejava.project.bookstore.ports.input;

import com.onlinejava.project.bookstore.domain.entity.Book;
import com.onlinejava.project.bookstore.domain.entity.Purchase;

import java.util.List;

public interface PurchaseUseCase {
    void buyBook(String titleToBuy, String customer);

    int getPoint(Book book, String customer);

    List<Purchase> getPurchaseList();

    void printPurchaseListByUser(String userName);
}
