package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.core.cli.CliCommand;

import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.bookstore;
import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.scanner;

@CliCommand
public class PurchaseCommands {

    @CliCommand(ID = "6", title = "Print purchase list")
    public void printPurchaseList() {
        bookstore.printPurchaseList();
    }

    @CliCommand(ID = "12", title = "Print a user's purchases")
    public void printUsersPurchaseList() {
        System.out.printf("Type user name:");
        String userNameToPrintPurchases = scanner.nextLine().trim();
        bookstore.printPurchaseListByUser(userNameToPrintPurchases);
    }

}
