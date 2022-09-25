package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;
import com.onlinejava.project.bookstore.application.ports.input.PurchaseUseCase;
import com.onlinejava.project.bookstore.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.application.ports.output.MemberRepository;
import com.onlinejava.project.bookstore.application.ports.output.PurchaseRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;

import java.util.List;

@Bean
public class PurchaseService implements PurchaseUseCase {
    @Inject
    private PurchaseRepository repository;
    @Inject
    private BookRepository bookRepository;
    @Inject
    private MemberRepository memberRepository;

    public PurchaseService() {
    }

    public PurchaseService(PurchaseRepository repository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void buyBook(String title, String customer) {
        bookRepository.findByTitle(title).stream()
                .filter(book -> book.getStock() > 0)
                .forEach(book -> {
                    book.setStock(book.getStock() - 1);
                    Purchase purchase = new Purchase();
                    purchase.setTitle(title);
                    purchase.setCustomer(customer);
                    purchase.setNumberOfPurchase(1);
                    purchase.setTotalPrice(book.getPrice());
                    purchase.setPoint(getPoint(book, customer));
                    repository.add(purchase);
                    memberRepository.findByUserName(customer)
                            .ifPresent(member -> member.addPoint(getPoint(book, customer)));
                });
    }

    @Override
    public int getPoint(Book book, String customer) {
        return memberRepository.findByUserName(customer)
                .map(m -> getPointByMember(book, m))
                .orElseThrow();
    }

    private int getPointByMember(Book book, Member member) {
        return member.getGrade().calculatePoint(book.getPrice());
    }

    @Override
    public List<Purchase> getPurchaseList() {
        return repository.findAll();
    }

    @Override
    public List<Purchase> getPurchaseListByUser(String userName) {
        return getPurchaseList().stream()
                .filter(purchase -> purchase.getCustomer().equals(userName))
                .toList();
    }
}
