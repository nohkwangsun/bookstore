package com.onlinejava.project.bookstore;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        BookStore bookStore = new BookStore();

        while(true) {
            bookStore.printWelcomePage();
            bookStore.runCommand(scanner);
        }
    }
}
