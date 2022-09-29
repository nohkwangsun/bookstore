package com.onlinejava.project.bookstore.adapters.file;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;
import com.onlinejava.project.bookstore.application.ports.output.PurchaseRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;

import java.util.List;

@Bean
public class FilePurchaseRepository extends FileRepository<Purchase> implements PurchaseRepository {
    @Override
    public List<Purchase> findAll() {
        return this.list == null ? List.of() : this.list;
    }

    @Override
    public void save() {
        saveEntityToCSVFile("purchaselist.csv", Purchase.class, Main.HAS_HEADER);
    }

    @Override
    public boolean add(Purchase purchase) {
        return this.list.add(purchase);
    }

    @Override
    public List<Purchase> findByCustomer(String customerName) {
        return this.list.stream()
                .filter(p -> p.getCustomer().equals(customerName))
                .toList();
    }

    @Override
    public void initData() {
        this.list = getEntityListFromLines("purchaselist.csv", Purchase.class);
    }
}
