package com.onlinejava.project.bookstore.application.domain;

import com.onlinejava.project.bookstore.adapters.file.FileDataInitializer;
import com.onlinejava.project.bookstore.application.domain.exception.ExpectedException;
import com.onlinejava.project.bookstore.application.domain.exception.UnexpectedException;
import com.onlinejava.project.bookstore.application.domain.exception.UnknownCommandException;
import com.onlinejava.project.bookstore.core.cli.*;

import java.util.Scanner;

import static com.onlinejava.project.bookstore.core.util.ExceptionUtils.getRootCause;

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
        FileDataInitializer.initializeRepositoryData();
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
        try {
            String cmdNum = scanner.nextLine().trim();
            runCommand(cmdNum);
        } catch (ExpectedException e) {
            System.out.println("\nInfo : " + e.getMessage());
        } catch (UnexpectedException e) {
            System.out.println("\nError : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError : " + getRootCause(e).getMessage());
        }
        System.out.println("Press enter for the menu...");
        scanner.nextLine();
    }

    public void runCommand(String cmdNum) {
        CliCommandInterface command = CommandCache.getCommand(cmdNum)
                .orElseThrow(() -> new UnknownCommandException(cmdNum));
        command.run();
    }

}