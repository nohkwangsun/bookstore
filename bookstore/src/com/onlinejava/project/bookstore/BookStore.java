package com.onlinejava.project.bookstore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class BookStore {


    private List<Book> bookList;
    private List<Purchase> purchaseList;

    {
        purchaseList = new ArrayList<>();
        bookList = new ArrayList<>();
        bookList.add(new Book("홍길동", "작자미상", "조선사", 10000, "01/01/1500", "2층 10번"));
        bookList.add(new Book("홍길동1", "작자미상", "조선사", 10000, "01/01/1500", "2층 10번"));
        bookList.add(new Book("홍길동2", "작자미상", "조선사", 10000, "01/01/1500", "2층 10번"));
    }

    public void printWelcomePage() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("======================================================================");
        System.out.println("=                                                                    =");
        System.out.println("=                                                                    =");
        System.out.println("=                   Welcome to Bookstore                             =");
        System.out.println("=                                                                    =");
        System.out.println("=            ------------------------------------                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |     1. Print book list           |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |     2. Book Search               |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |     3. Add new book              |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |     4. Delete a book             |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |     5. buy a book                |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |     6. Print purchase list       |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |     q. Quit                      |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            ------------------------------------                    =");
        System.out.println("=                                                                    =");
        System.out.println("======================================================================");
        System.out.print("Type the number of the command you want to run:");
    }

    public void runCommand(Scanner scanner) {
        String command = scanner.nextLine().trim();
        switch (command) {
            case "1":
                printBookList();
                break;
            case "2":
                System.out.print("Search Keyword:");
                String keyword = scanner.nextLine();
                searchBook(keyword);
                break;
            case "3":
                System.out.printf("Type title:");
                String title = scanner.nextLine().trim();

                System.out.printf("Type writer:");
                String writer = scanner.nextLine().trim();

                System.out.printf("Type publisher:");
                String publisher = scanner.nextLine().trim();

                System.out.printf("Type price:");
                int price = Integer.parseInt( scanner.nextLine().trim() );

                System.out.printf("Type releaseDate:");
                String releaseDate = scanner.nextLine().trim();

                System.out.printf("Type location:");
                String location = scanner.nextLine().trim();

                Book newBook = new Book(title, writer, publisher, price, releaseDate, location);
                createBook(newBook);
                break;
            case "4":
                System.out.printf("Type title:");
                String deletingTitle = scanner.nextLine().trim();
                deleteBook(deletingTitle);
                break;
            case "5":
                System.out.printf("Type title:");
                String titleToBuy = scanner.nextLine().trim();
                System.out.printf("Type customer:");
                String customer = scanner.nextLine().trim();
                buyBook(titleToBuy, customer);
                break;
            case "6":
                printPurchaseList();
                break;
            case "q":
                System.exit(0);
                break;
            default:
                System.out.println("Error: Unknown command : " + command);
        }
        System.out.println("Press enter for the menu...");
        scanner.nextLine();
    }

    private void printPurchaseList() {
        getPurchaseList().stream()
                .forEach(System.out::println);
    }

    private void buyBook(String titleToBuy, String customer) {
        getBookList().stream()
                .filter(book -> book.getTitle().equals(titleToBuy))
                .filter(book -> book.getStock() > 0)
                .forEach(book -> {
                    book.setStock(book.getStock() - 1);
                    Purchase purchase = new Purchase(titleToBuy, customer, 1);
                    getPurchaseList().add(purchase);
                });
    }

    private List<Purchase> getPurchaseList() {
        return this.purchaseList;
    }

    private void deleteBook(String deletingTitle) {
        getBookList().stream()
                .filter(book -> book.getTitle().equals(deletingTitle))
                .findFirst()
                .ifPresent(getBookList()::remove);
    }

    private void createBook(Book newBook) {
        getBookList().add(newBook);
    }

    private void searchBook(String keyword) {
        printTableLine();
        printHeader();
        getBookList().stream()
                .filter(book -> book.getTitle().contains(keyword))
                .forEach(this::printTable);
        printTableLine();
    }

    private void printBookList() {
        printHeader();
        getBookList().forEach(this::printTable);
        printTableLine();

    }

    private void printHeader() {
        printTableLine();
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |\n",
                "TITLE", "WRITER", "PRICE", "LOCATION", "STOCK");
    }

    private void printTable(Book book) {
        printTableLine();
        System.out.printf("| %-10s \t | %-10s \t | %-10d \t | %-10s \t | %-10d \t |\n",
                book.getTitle(), book.getWriter(), book.getPrice(), book.getLocation(), book.getStock());
    }

    private void printTableLine() {
        IntStream.range(1, 60).forEach(i -> System.out.print("-"));
        System.out.println();
    }

    private List<Book> getBookList() {
        return bookList;
    }

}
