package com.onlinejava.project.bookstore.client.adapters.in.cli;

import com.onlinejava.project.bookstore.client.application.ports.in.BookUseCase;
import com.onlinejava.project.bookstore.common.domain.entity.Book;
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
}
