package com.onlinejava.project.bookstore.admin.application.ports.output;

import com.onlinejava.project.bookstore.common.domain.entity.Purchase;

import java.util.List;

public interface PurchaseRepository extends Repository {
    List<Purchase> findAll();

    boolean add(Purchase purchase);

    List<Purchase> findByCustomer(String customerName);
}
