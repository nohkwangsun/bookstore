package com.onlinejava.project.bookstore.admin.adapters.cli;

import com.onlinejava.project.bookstore.common.domain.entity.Purchase;
import com.onlinejava.project.bookstore.admin.application.ports.input.PurchaseUseCase;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.core.cli.ConsolePrinter;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

import static com.onlinejava.project.bookstore.core.cli.ConsoleScanner.prompt;

@CliCommand
@SuppressWarnings({"unused"})
public class PurchaseCommands {
    private PurchaseUseCase service;

    private ConsolePrinter<Purchase> printer = new ConsolePrinter<>(Purchase.class);

    public PurchaseCommands() {
        service = BeanFactory.getInstance().get(PurchaseUseCase.class);
    }

    public PurchaseCommands(PurchaseUseCase service) {
        this.service = service;
    }

    @CliCommand(ID = "6", title = "Print purchase list")
    public void printPurchaseList() {
        printer.printList(service.getPurchaseList());
    }

    @CliCommand(ID = "5", title = "Buy a book")
    public void buyBookCommand() {
        String title = prompt("title");
        String customer = prompt("customer");
        service.buyBook(title, customer);
    }

    @CliCommand(ID = "12", title = "Print a user's purchases")
    public void printUsersPurchaseList() {
        String userName = prompt("user name");
        printer.printList(service.getPurchaseListByUser(userName));
    }

}
