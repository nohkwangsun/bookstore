package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.core.cli.CliCommand;

import static com.onlinejava.project.bookstore.Main.scanner;
import static com.onlinejava.project.bookstore.domain.service.BookStoreService.bookStoreService;

@CliCommand
@SuppressWarnings({"unused"})
public class PurchaseCommands {

    @CliCommand(ID = "6", title = "Print purchase list")
    public void printPurchaseList() {
        bookStoreService.printPurchaseList();
    }

    @CliCommand(ID = "12", title = "Print a user's purchases")
    public void printUsersPurchaseList() {
        System.out.print("Type user name:");
        String userNameToPrintPurchases = scanner.nextLine().trim();
        bookStoreService.printPurchaseListByUser(userNameToPrintPurchases);
    }

}
