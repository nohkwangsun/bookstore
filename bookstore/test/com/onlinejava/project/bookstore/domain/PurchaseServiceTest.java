package com.onlinejava.project.bookstore.domain;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.domain.entity.Grade;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;
import com.onlinejava.project.bookstore.domain.entity.*;
import com.onlinejava.project.bookstore.application.domain.service.PurchaseService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PurchaseServiceTest {

    @Test
    void buyBook() {
        PurchaseRepositoryStub purchaseRepository = new PurchaseRepositoryStub(){
            public List<Purchase> list = new ArrayList<>();

            @Override
            public List<Purchase> findAll() {
                return this.list;
            }

            @Override
            public void add(Purchase purchase) {
                this.list.add(purchase);
            }
        };
        BookRepositoryStub bookRepository = new BookRepositoryStub() {
            public List<Book> list = List.of(
                    new Book() {{
                        setTitle("홍길동전");
                        setPrice(12020);
                        setStock(2);
                    }}
            );

            @Override
            public Optional<Book> findByTitle(String title) {
                return Optional.of(list.get(0));
            }
        };
        MemberRepositoryStub memberRepository = new MemberRepositoryStub() {
            public List<Member> list = List.of(
                    new Member() {{
                        setUserName("김종국");
                        setGrade(Grade.BRONZE);
                        setTotalPoint(100_000);
                    }},
                    new Member() {{
                        setUserName("유재석");
                        setGrade(Grade.SILVER);
                        setTotalPoint(200_000);
                    }}
            );

            @Override
            public Optional<Member> findByUserName(String userName) {
                return list.stream().filter(u -> u.getUserName().equals(userName)).findFirst();
            }
        };
        PurchaseService service = new PurchaseService(purchaseRepository, bookRepository, memberRepository);

        service.buyBook("홍길동전", "김종국");

        assertEquals(1, purchaseRepository.findAll().size());
        assertEquals(100024, memberRepository.findByUserName("김종국").get().getTotalPoint());
        assertEquals(1, bookRepository.findByTitle("홍길동전").get().getStock());
    }
}
