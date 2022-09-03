package com.onlinejava.project.bookstore.cli;

import com.onlinejava.project.bookstore.BookStore;

import java.util.Scanner;

public interface CliCommandInterface {
    Scanner scanner = new Scanner(System.in);
    BookStore bookstore = new BookStore();

    String getCommandID();

    String getTitle();

    String getDescription();

    public void run();

    public default int order() {
        return Integer.MAX_VALUE;
    }
}
