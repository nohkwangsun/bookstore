package com.onlinejava.project.bookstore.clicommands;

public class PrintBookListCommand implements CliCommand {
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
