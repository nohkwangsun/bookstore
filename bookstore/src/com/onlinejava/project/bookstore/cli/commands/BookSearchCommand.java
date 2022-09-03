package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.core.cli.CliCommandInterface;

public class BookSearchCommand implements CliCommandInterface {
    @Override
    public String getCommandID() {
        return "2";
    }

    @Override
    public String getTitle() {
        return "Book Search";
    }

    @Override
    public String getDescription() {
        return "Print the books you want to search.";
    }

    @Override
    public void run() {
        System.out.print("Search Keyword:");
        String keyword = scanner.nextLine();
        bookstore.printBookList(bookstore.searchBook(keyword));
    }
}
