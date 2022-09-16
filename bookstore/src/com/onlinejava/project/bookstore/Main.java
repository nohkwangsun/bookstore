package com.onlinejava.project.bookstore;

import com.onlinejava.project.bookstore.domain.service.BookStoreService;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        BookStoreService bookStore = new BookStoreService();

        while(true) {
            bookStore.printWelcomePage();
            bookStore.runCommand(scanner);
        }
    }
}
