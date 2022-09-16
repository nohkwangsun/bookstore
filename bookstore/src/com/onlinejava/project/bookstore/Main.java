package com.onlinejava.project.bookstore;

import com.onlinejava.project.bookstore.domain.model.Book;
import com.onlinejava.project.bookstore.domain.model.Member;
import com.onlinejava.project.bookstore.domain.model.Purchase;
import com.onlinejava.project.bookstore.domain.service.BookStoreService;

import java.util.Scanner;

public class Main {

    public static final boolean HAS_HEADER = true;
    public static final String COMMAND_PACKAGE = "com.onlinejava.project.bookstore.cli.commands";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        BookStoreService bookStore = new BookStoreService();
        bookStore.setBookList(bookStore.getModelListFromLines("booklist.csv", Book.class));
        bookStore.setPurchaseList(bookStore.getModelListFromLines("purchaselist.csv", Purchase.class));
        bookStore.setMemberList(bookStore.getModelListFromLines("memberlist.csv", Member.class));

        while(true) {
            bookStore.printWelcomePage();
            bookStore.runCommand(scanner);
        }
    }
}
