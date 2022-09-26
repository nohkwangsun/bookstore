package com.onlinejava.project.bookstore.adapters.file;

import com.onlinejava.project.bookstore.application.domain.entity.Grade;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilePurchaseRepositoryTest {
    private static int CALL_COUNT = 0;

    FilePurchaseRepository repository = new FilePurchaseRepository() {
        @Override
        public List<Purchase> getEntityListFromLines(String filePath, Class<Purchase> clazz) {
            List<Purchase> list = new ArrayList<>();
            list.add(new Purchase("refactoring", "gary", 1, 10000, 10));
            list.add(new Purchase("refactoring", "you jaesuk", 1, 100000, 10));
            list.add(new Purchase("design pattern", "you jaesuk", 1, 100000, 10));
            return list;
        }

        @Override
        public void saveEntityToCSVFile(String fileName, Class<Purchase> clazz, boolean hasHeader) {
            CALL_COUNT++;
        }
    };

    @BeforeEach
    void beforeEach() {
        repository.initData();
    }

    @Test
    void initData() {
        repository.list = null;
        repository.initData();
        assertEquals(3, repository.list.size());
    }

    @Test
    void findAll() {
        List<Purchase> list = repository.findAll();

        assertEquals(3, list.size());
    }

    @Test
    void save() {
        CALL_COUNT = 0;

        repository.save();

        assertEquals(1, CALL_COUNT);
    }

    @Test
    void add() {
        Purchase purchase = new Purchase("refactoring", "haha", 1, 50000, (int)(50000*0.005));

        repository.add(purchase);

        assertEquals(4, repository.list.size());
        assertEquals("haha", repository.list.get(3).getCustomer());
    }
}