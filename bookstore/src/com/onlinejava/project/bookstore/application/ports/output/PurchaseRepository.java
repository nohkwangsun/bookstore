package com.onlinejava.project.bookstore.application.ports.output;

import com.onlinejava.project.bookstore.application.domain.entity.Purchase;

import java.util.List;

public interface PurchaseRepository extends Repository {
    List<Purchase> findAll();

    boolean add(Purchase purchase);

    List<Purchase> findByCustomer(String customerName);
}
