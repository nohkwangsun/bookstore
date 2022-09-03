package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.Book;
import com.onlinejava.project.bookstore.core.cli.CliCommand;

import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.bookstore;
import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.scanner;

@CliCommand
public class BookCommands {

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

}
