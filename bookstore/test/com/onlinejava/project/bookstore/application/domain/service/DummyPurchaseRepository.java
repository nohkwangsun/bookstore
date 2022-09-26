package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.entity.Purchase;
import com.onlinejava.project.bookstore.application.ports.output.PurchaseRepository;

import java.util.List;

public class DummyPurchaseRepository implements PurchaseRepository {
    @Override
    public List<Purchase> findAll() {
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public void add(Purchase purchase) {

    }
}
