package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.core.cli.CliCommand;

import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.bookstore;
import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.scanner;

@CliCommand
@SuppressWarnings({"unused"})
public class GradeCommands {

    @CliCommand(ID = "13", title = "Update member's grades")
    public void printUsersPurchaseList() {
        System.out.print("Do you want to update the grades of all members? [y(default) / n]:");
        String yn = scanner.nextLine().trim();
        if (yn.equalsIgnoreCase("Y")) {
            bookstore.updateMemberGrades();
        } else {
            System.out.println("Canceled");
        }
    }

}
