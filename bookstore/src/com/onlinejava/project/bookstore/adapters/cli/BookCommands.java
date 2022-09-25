package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.ports.input.BookUseCase;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

import java.util.List;
import java.util.stream.IntStream;

import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;

@CliCommand
@SuppressWarnings({"unused"})
public class BookCommands {
    private BookUseCase service;

    private ConsolePrinter<Book> printer = new ConsolePrinter<>(Book.class);

    public BookCommands() {
        service = BeanFactory.getInstance().get(BookUseCase.class);
    }

    public BookCommands(BookUseCase service) {
        this.service = service;
    }

    @CliCommand(ID = "1", title = "Print book list")
    public void printBookList() {
        printer.printList(service.getBookList());
    }
    
    @CliCommand(ID = "2", title = "Search a book")
    public void searchBook() {
        System.out.print("Search Keyword:");
        String keyword = scanner.nextLine();
        printer.printList(service.searchBook(keyword));
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
