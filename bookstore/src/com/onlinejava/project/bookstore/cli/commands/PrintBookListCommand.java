package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.core.cli.CliCommandInterface;

public class PrintBookListCommand implements CliCommandInterface {
    @Override
    public String getCommandID() {
        return "1";
    }

    @Override
    public String getTitle() {
        return "Print book list";
    }

    @Override
    public String getDescription() {
        return "Print all books the bookstore has.";
    }

    @Override
    public void run() {
        bookstore.printBookList(bookstore.getBookList());
    }
}
