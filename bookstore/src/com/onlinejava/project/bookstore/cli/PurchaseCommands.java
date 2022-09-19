package com.onlinejava.project.bookstore.cli;

import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.domain.BookStoreFactory;
import com.onlinejava.project.bookstore.ports.input.PurchaseUseCase;

import static com.onlinejava.project.bookstore.domain.BookStoreApplication.scanner;

@CliCommand
@SuppressWarnings({"unused"})
public class PurchaseCommands {
    private PurchaseUseCase service;

    public PurchaseCommands() {
        this.service = BookStoreFactory.lookup(PurchaseUseCase.class);
    }

    public PurchaseCommands(PurchaseUseCase service) {
        this.service = service;
    }

    @CliCommand(ID = "6", title = "Print purchase list")
    public void printPurchaseList() {
        service.getPurchaseList().forEach(System.out::println);
    }

    @CliCommand(ID = "5", title = "Buy a book")
    public void buyBookCommand() {
        System.out.print("Type title:");
        String titleToBuy = scanner.nextLine().trim();
        System.out.print("Type customer:");
        String customer = scanner.nextLine().trim();
        service.buyBook(titleToBuy, customer);
    }

    @CliCommand(ID = "12", title = "Print a user's purchases")
    public void printUsersPurchaseList() {
        System.out.print("Type user name:");
        String userNameToPrintPurchases = scanner.nextLine().trim();
        service.printPurchaseListByUser(userNameToPrintPurchases);
    }

}
