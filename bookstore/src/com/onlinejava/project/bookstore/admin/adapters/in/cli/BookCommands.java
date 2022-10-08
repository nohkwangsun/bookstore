package com.onlinejava.project.bookstore.admin.adapters.in.cli;

import com.onlinejava.project.bookstore.common.domain.entity.Book;
import com.onlinejava.project.bookstore.admin.application.ports.input.BookUseCase;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.core.cli.ConsolePrinter;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

import static com.onlinejava.project.bookstore.core.cli.ConsoleScanner.prompt;


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
        String keyword = prompt("keyword");
        printer.printList(service.searchBook(keyword));
    }

    @CliCommand(ID = "3", title = "Add a new book")
    public void addBookCommand() {
        String title = prompt("title");
        String writer = prompt("writer");
        String publisher = prompt("publisher");
        int price = prompt("price", Integer::parseInt);
        String releaseDate = prompt("releaseDate");
        String location = prompt("location");

        Book newBook = new Book(title, writer, publisher, price, releaseDate, location, 0);
        service.createBook(newBook);
    }

    @CliCommand(ID = "4", title = "Delete a book")
    public void deleteBookCommand() {
        String title = prompt("title");
        service.deleteBook(title);
    }

    @CliCommand(ID = "7", title = "Add book stock")
    public void addBookStock() {
        String title = prompt("title");
        int stock = prompt("stock", Integer::parseInt);
        service.addStock(title, stock);
    }

}
