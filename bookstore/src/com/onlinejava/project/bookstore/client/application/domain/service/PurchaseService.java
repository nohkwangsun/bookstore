package com.onlinejava.project.bookstore.client.application.domain.service;

import com.onlinejava.project.bookstore.client.application.domain.exception.NoSuchItemException;
import com.onlinejava.project.bookstore.client.application.domain.exception.NotEnoughBooksInStockException;
import com.onlinejava.project.bookstore.client.application.ports.input.PurchaseUseCase;
import com.onlinejava.project.bookstore.client.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.client.application.ports.output.MemberRepository;
import com.onlinejava.project.bookstore.client.application.ports.output.PurchaseRepository;
import com.onlinejava.project.bookstore.common.domain.entity.Book;
import com.onlinejava.project.bookstore.common.domain.entity.Member;
import com.onlinejava.project.bookstore.common.domain.entity.Purchase;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;
import com.onlinejava.project.bookstore.core.util.validator.Validators;

import java.util.List;

import static com.onlinejava.project.bookstore.common.domain.exception.TooManyItemsException.Term.BookTitle;
import static com.onlinejava.project.bookstore.common.domain.exception.TooManyItemsException.Term.MemberName;

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
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(NoSuchItemException.supplierOf(BookTitle, title));
        throwIfNotEnoughStock(title, book);
        throwIfNoSuchMember(customer);
        updateStockWithMinusOne(book);

        int point = getPoint(book, customer);
        addNewPurchase(title, customer, book, point);
        updateUserPoint(customer, point);
    }

    private Member throwIfNoSuchMember(String customer) {
        return memberRepository.findByUserName(customer).orElseThrow(NoSuchItemException.supplierOf(MemberName, customer));
    }

    private void throwIfNotEnoughStock(String title, Book book) {
        if (book.getStock() <= 0) {
            throw new NotEnoughBooksInStockException(BookTitle, title);
        }
    }

    private void updateUserPoint(String customer, int point) {
        throwIfNoSuchMember(customer)
                .addPoint(point);
    }

    private void addNewPurchase(String title, String customer, Book book, int point) {
        Purchase purchase = new Purchase(title, customer, 1, book.getPrice(), point);
        repository.add(purchase);
    }

    private void updateStockWithMinusOne(Book book) {
        book.setStock(book.getStock() - 1);
    }

    private int getPoint(Book book, String customer) {
        return memberRepository.findByUserName(customer)
                .map(m -> getPointByMember(book, m))
                .orElseThrow(NoSuchItemException.supplierOf(MemberName, customer));
    }

    private int getPointByMember(Book book, Member member) {
        return member.getGrade().calculatePoint(book.getPrice());
    }

    @Override
    public List<Purchase> getPurchaseListByUser(String userName) {
        List<Purchase> list = repository.findByCustomer(userName);
        Validators.throwIfEmpty(list, Purchase.class);
        return list;
    }
}
