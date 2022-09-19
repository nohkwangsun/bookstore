package com.onlinejava.project.bookstore.application.domain;

import com.onlinejava.project.bookstore.core.cli.*;

import java.util.*;

public class BookStoreApplication {

    public static final Scanner scanner = new Scanner(System.in);
    private boolean keepRunning = true;

    public boolean isKeepRunning() {
        return keepRunning;
    }

    public void setKeepRunning(boolean keepRunning) {
        this.keepRunning = keepRunning;
    }

    public void run() {
        initialize();
        while (keepRunning) {
            printWelcomePage();
            runCommand();
        }
        System.out.println("End");
    }

    public void initialize() {
        BookStoreFactory.loadObjectsIntoCache();
        BookStoreFactory.initializeRepositoryData();
        CommandCache.loadCommands();
    }

    public void printWelcomePage() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("==============================================================");
        System.out.println("=                                                            =");
        System.out.println("=                                                            =");
        System.out.println("=                   Welcome to Bookstore                     =");
        System.out.println("=                                                            =");
        System.out.println("=            ------------------------------------            =");
        System.out.println("=            |                                  |            =");

        CommandCache.getCommands().stream()
                .sorted(CliCommandInterface::ordering)
                .forEach(command -> {
                    System.out.printf("%-13s|%6s. %-26s|%13s\n", "=", command.getCommandID(), command.getTitle(), "=");
                    System.out.printf("%-13s|%6s  %-26s|%13s\n", "=", "", "", "=");
                });

        System.out.println("=            ------------------------------------            =");
        System.out.println("=                                                            =");
        System.out.println("==============================================================");
        System.out.print("Type the number of the command you want to run:");
    }

    public void runCommand() {
        String cmdNum = scanner.nextLine().trim();
        CommandCache.getCommand(cmdNum).ifPresentOrElse(
                CliCommandInterface::run,
                () -> System.out.println("Error: Unknown command : " + cmdNum)
        );

    }

}