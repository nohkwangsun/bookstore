package com.onlinejava.project.bookstore.infrastructure.file;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.domain.entity.Purchase;
import com.onlinejava.project.bookstore.ports.output.PurchaseRepository;

import java.util.List;

public class FilePurchaseRepository extends FileRepository<Purchase> implements PurchaseRepository {
    @Override
    public List<Purchase> findAll() {
        return this.list;
    }

    @Override
    public void save() {
        saveEntityToCSVFile("purchaselist.csv", Purchase.class, Main.HAS_HEADER);
    }

    @Override
    public void add(Purchase purchase) {
        this.list.add(purchase);
    }

    @Override
    public void initData() {
        this.list = getEntityListFromLines("purchaselist.csv", Purchase.class);
    }
}
