package com.onlinejava.project.bookstore.clicommands;

public class BookSearchCommand implements CliCommand {
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
