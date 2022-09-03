package com.onlinejava.project.bookstore.clicommands;

import com.onlinejava.project.bookstore.BookStore;

import java.util.Scanner;

public interface CliCommand {
    Scanner scanner = new Scanner(System.in);
    BookStore bookstore = new BookStore();


    public String getCommandID();

    public String getTitle();

    public String getDescription();

    public void run();

    public default int order() {
        return Integer.MAX_VALUE;
    }
}
