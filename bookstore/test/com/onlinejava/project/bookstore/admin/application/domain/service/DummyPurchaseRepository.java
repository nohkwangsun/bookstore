package com.onlinejava.project.bookstore.admin.application.domain.service;

import com.onlinejava.project.bookstore.common.domain.entity.Purchase;
import com.onlinejava.project.bookstore.admin.application.ports.output.PurchaseRepository;

import java.util.List;

public class DummyPurchaseRepository implements PurchaseRepository {
    @Override
    public List<Purchase> findAll() {
        return null;
    }

    @Override
    public boolean add(Purchase purchase) {

        return false;
    }

    @Override
    public List<Purchase> findByCustomer(String customerName) {
        return null;
    }
}
