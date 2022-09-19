package com.onlinejava.project.bookstore.ports.output;

import com.onlinejava.project.bookstore.domain.entity.Purchase;

import java.util.List;

public interface PurchaseRepository {
    List<Purchase> findAll();
    void save();

    void add(Purchase purchase);
}
