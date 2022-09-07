package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.Book;
import com.onlinejava.project.bookstore.core.cli.CliCommand;

import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.bookstore;
import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.scanner;

@CliCommand
public class BookCommands {
    @CliCommand(ID = "1", title = "Print book list")
    public void printBookList() {
        bookstore.printBookList(bookstore.getBookList());
    }
    
    @CliCommand(ID = "2", title = "Search a book")
    public void searchBook() {
        System.out.print("Search Keyword:");
        String keyword = scanner.nextLine();
        bookstore.printBookList(bookstore.searchBook(keyword));
    }

    @CliCommand(ID = "3", title = "Add a new book")
    public void addBookCommand() {
        System.out.printf("Type title:");
        String title = scanner.nextLine().trim();

        System.out.printf("Type writer:");
        String writer = scanner.nextLine().trim();

        System.out.printf("Type publisher:");
        String publisher = scanner.nextLine().trim();

        System.out.printf("Type price:");
        int price = Integer.parseInt( scanner.nextLine().trim() );

        System.out.printf("Type releaseDate:");
        String releaseDate = scanner.nextLine().trim();

        System.out.printf("Type location:");
        String location = scanner.nextLine().trim();

        Book newBook = new Book(title, writer, publisher, price, releaseDate, location);
        bookstore.createBook(newBook);
    }

    @CliCommand(ID = "4", title = "Delete a book")
    public void deleteBookCommand() {
        System.out.printf("Type title:");
        String deletingTitle = scanner.nextLine().trim();
        bookstore.deleteBook(deletingTitle);
    }

    @CliCommand(ID = "5", title = "Buy a book")
    public void buyBookCommand() {
        System.out.printf("Type title:");
        String titleToBuy = scanner.nextLine().trim();
        System.out.printf("Type customer:");
        String customer = scanner.nextLine().trim();
        bookstore.buyBook(titleToBuy, customer);
    }

    @CliCommand(ID = "7", title = "Add book stock")
    public void addBookStock() {
        System.out.printf("Type title:");
        String titleToAddStock = scanner.nextLine().trim();
        System.out.printf("Type stock:");
        int stock = Integer.parseInt(scanner.nextLine().trim());
        bookstore.addStock(titleToAddStock, stock);
    }
}
