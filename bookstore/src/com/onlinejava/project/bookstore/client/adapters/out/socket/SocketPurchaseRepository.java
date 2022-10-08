package com.onlinejava.project.bookstore.client.adapters.out.socket;

import com.onlinejava.project.bookstore.client.application.ports.out.PurchaseRepository;
import com.onlinejava.project.bookstore.common.domain.entity.Purchase;
import com.onlinejava.project.bookstore.core.factory.Bean;

import java.util.List;

@Bean
public class SocketPurchaseRepository implements PurchaseRepository {
    @Override
    public boolean add(Purchase purchase) {
        return false;
    }

    @Override
    public List<Purchase> findByCustomer(String customerName) {
        return List.of();
    }
}
