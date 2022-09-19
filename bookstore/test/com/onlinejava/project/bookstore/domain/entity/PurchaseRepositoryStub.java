package com.onlinejava.project.bookstore.domain.entity;

import com.onlinejava.project.bookstore.domain.entity.Purchase;
import com.onlinejava.project.bookstore.ports.output.PurchaseRepository;

import java.util.List;

public class PurchaseRepositoryStub implements PurchaseRepository {
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
