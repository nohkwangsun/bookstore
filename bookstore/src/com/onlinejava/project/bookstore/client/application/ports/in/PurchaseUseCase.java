package com.onlinejava.project.bookstore.client.application.ports.in;

import com.onlinejava.project.bookstore.common.domain.entity.Purchase;

import java.util.List;

public interface PurchaseUseCase {
    void buyBook(String titleToBuy, String customer);

    List<Purchase> getPurchaseListByUser(String userName);
}
