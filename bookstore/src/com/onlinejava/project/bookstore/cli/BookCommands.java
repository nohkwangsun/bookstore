package com.onlinejava.project.bookstore.cli;

import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.domain.entity.Book;
import com.onlinejava.project.bookstore.domain.BookStoreFactory;
import com.onlinejava.project.bookstore.ports.input.BookUseCase;

import static com.onlinejava.project.bookstore.domain.BookStoreApplication.scanner;

@CliCommand
@SuppressWarnings({"unused"})
public class BookCommands {
    private BookUseCase service;

    public BookCommands() {
        this.service = BookStoreFactory.lookup(BookUseCase.class);
    }

    public BookCommands(BookUseCase service) {
        this.service = service;
    }

    @CliCommand(ID = "1", title = "Print book list")
    public void printBookList() {
        service.printBookList(service.getBookList());
    }
    
    @CliCommand(ID = "2", title = "Search a book")
    public void searchBook() {
        System.out.print("Search Keyword:");
        String keyword = scanner.nextLine();
        service.printBookList(service.searchBook(keyword));
    }

    @CliCommand(ID = "3", title = "Add a new book")
    public void addBookCommand() {
        System.out.print("Type title:");
        String title = scanner.nextLine().trim();

        System.out.print("Type writer:");
        String writer = scanner.nextLine().trim();

        System.out.print("Type publisher:");
        String publisher = scanner.nextLine().trim();

        System.out.print("Type price:");
        int price = Integer.parseInt( scanner.nextLine().trim() );

        System.out.print("Type releaseDate:");
        String releaseDate = scanner.nextLine().trim();

        System.out.print("Type location:");
        String location = scanner.nextLine().trim();

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setTitle(writer);
        newBook.setPublisher(publisher);
        newBook.setPrice(price);
        newBook.setReleaseDate(releaseDate);
        newBook.setLocation(location);
        service.createBook(newBook);
    }

    @CliCommand(ID = "4", title = "Delete a book")
    public void deleteBookCommand() {
        System.out.print("Type title:");
        String deletingTitle = scanner.nextLine().trim();
        service.deleteBook(deletingTitle);
    }

    @CliCommand(ID = "7", title = "Add book stock")
    public void addBookStock() {
        System.out.print("Type title:");
        String titleToAddStock = scanner.nextLine().trim();
        System.out.print("Type stock:");
        int stock = Integer.parseInt(scanner.nextLine().trim());
        service.addStock(titleToAddStock, stock);
    }
}
