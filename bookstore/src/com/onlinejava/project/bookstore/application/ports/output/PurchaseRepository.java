package com.onlinejava.project.bookstore.application.ports.output;

import com.onlinejava.project.bookstore.application.domain.entity.Purchase;

import java.util.List;

public interface PurchaseRepository {
    List<Purchase> findAll();
    void save();

    void add(Purchase purchase);
}
