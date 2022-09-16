package com.onlinejava.project.bookstore;

import com.onlinejava.project.bookstore.domain.model.Book;
import com.onlinejava.project.bookstore.domain.model.Member;
import com.onlinejava.project.bookstore.domain.model.Purchase;

import java.util.Scanner;

import static com.onlinejava.project.bookstore.domain.service.BookStoreService.bookStoreService;

public class Main {

    public static final boolean HAS_HEADER = true;
    public static final String COMMAND_PACKAGE = "com.onlinejava.project.bookstore.cli.commands";
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        bookStoreService.setBookList(bookStoreService.getModelListFromLines("booklist.csv", Book.class));
        bookStoreService.setPurchaseList(bookStoreService.getModelListFromLines("purchaselist.csv", Purchase.class));
        bookStoreService.setMemberList(bookStoreService.getModelListFromLines("memberlist.csv", Member.class));

        while(true) {
            bookStoreService.printWelcomePage();
            bookStoreService.runCommand(scanner);
        }
    }
}
