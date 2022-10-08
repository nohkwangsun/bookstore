package com.onlinejava.project.bookstore.admin.application.domain.service;

import com.onlinejava.project.bookstore.common.domain.entity.Book;
import com.onlinejava.project.bookstore.common.domain.entity.Grade;
import com.onlinejava.project.bookstore.common.domain.entity.Member;
import com.onlinejava.project.bookstore.common.domain.entity.Purchase;
import com.onlinejava.project.bookstore.core.util.reflect.ReflectionUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PurchaseServiceTest {

    private class PurchaseRepositoryStub extends DummyPurchaseRepository {
        private List<Purchase> list;
        {
            list = new ArrayList<>();
            list.add(new Purchase("refactoring", "gary", 1, 10000, 10));
            list.add(new Purchase("refactoring", "you jaesuk", 1, 100000, 10));
            list.add(new Purchase("design pattern", "you jaesuk", 1, 100000, 10));
        }

        @Override
        public List<Purchase> findAll() {
            return list;
        }

        @Override
        public boolean add(Purchase purchase) {
            list.add(purchase);
            return false;
        }

        @Override
        public List<Purchase> findByCustomer(String customerName) {
            return this.list.stream().filter(p -> p.getCustomer().equals(customerName)).toList();
        }
    }
    private class BookRepositoryStub extends DummyBookRepository {
        private List<Book> list;
        {
            list = new ArrayList<>();
            list.add(new Book("refactoring", "martin", "addison", 50000, "1/1/2000", "b1", 1));
            list.add(new Book("design pattern", "gof", "addison", 40000, "2/2/2022", "b2", 10));
        }

        @Override
        public Optional<Book> findByTitle(String title) {
            return list.stream().filter(b -> b.getTitle().equals(title)).findFirst();
        }
    }
    private class MemberRepositoryStub extends DummyMemberRepository {
        public List<Member> list;
        {
            list = new ArrayList<>();
            list.add(new Member("haha", "haha@running.com", "11 sbs road", 100, Grade.SILVER, true));
            list.add(new Member("you jaesuk", "yjs@running.com", "13 sbs road", 1000, Grade.GOLD, true));
            list.add(new Member("gary", "gary@running.com", "3 sbs road", 3, Grade.GENERAL, false));
        }
        @Override
        public Optional<Member> findByUserName(String userName) {
            return list.stream().filter(m -> m.getUserName().equals(userName)).findFirst();
        }

    }
    private PurchaseRepositoryStub repository = new PurchaseRepositoryStub();
    private BookRepositoryStub bookRepository = new BookRepositoryStub();
    private MemberRepositoryStub memberRepository = new MemberRepositoryStub();
    private PurchaseService service = new PurchaseService(repository, bookRepository, memberRepository);

    @Test
    void purchaseService() {
        PurchaseService purchaseService = new PurchaseService();
        assertNotNull(purchaseService);
    }

    @Test
    void buyBook() {
        Purchase purchase = new Purchase("refactoring", "haha", 1, 50000, (int)(50000*0.005));

        service.buyBook("refactoring", "haha");

        assertEquals(4, repository.list.size());
        assertEquals(purchase, repository.list.get(3));
    }

    @Test
    void getPoint() {
        Book book = new Book("refactoring", "martin", "addison", 50000, "1/1/2000", "b1", 1);
        int point = ReflectionUtils.invokeMethod(service, "getPoint", Integer.class, book, "you jaesuk");
        assertEquals(500, point);
    }

    @Test
    void getPurchaseList() {
        List<Purchase> list = service.getPurchaseList();

        assertEquals(repository.list, list);
    }

    @Test
    void getPurchaseListByUser() {
        List<Purchase> purchases = service.getPurchaseListByUser("gary");

        assertEquals(1, purchases.size());
        assertEquals("refactoring", purchases.get(0).getTitle());
    }
}