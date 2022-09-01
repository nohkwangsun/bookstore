package com.onlinejava.project.bookstore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookStore {


    private List<Book> bookList;
    private List<Purchase> purchaseList;
    private List<Member> memberList;

    {
        try {
            bookList = Files.lines(Path.of("booklist.csv"))
                .map(line -> {
                    List<String> book = Arrays.stream(line.split(",")).map(String::trim).toList();
                    return new Book(book.get(0), book.get(1), book.get(2), Integer.parseInt(book.get(3)), book.get(4), book.get(5));
                }).collect(Collectors.toList());

            purchaseList = Files.lines(Path.of("purchaselist.csv"))
                    .map(line -> {
                        List<String> purchase = Arrays.stream(line.split(",")).map(String::trim).toList();
                        return new Purchase(purchase.get(0), purchase.get(1), Integer.parseInt(purchase.get(2)));
                    }).collect(Collectors.toList());

            memberList = Files.lines(Path.of("memberlist.csv"))
                    .map(line -> {
                        List<String> member = Arrays.stream(line.split(",")).map(String::trim).toList();
                        return new Member(member.get(0), member.get(1), member.get(2));
                    }).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

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
        System.out.println("=            |     7. Add book stock            |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |     8. Print member list         |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |     9. Add new member            |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |    10. Withdraw a member         |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |    11. Modify a member           |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |    12. Print a user's purchases  |                    =");
        System.out.println("=            |                                  |                    =");
        System.out.println("=            |     s. Save                      |                    =");
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
                printBookList(getBookList());
                break;
            case "2":
                System.out.print("Search Keyword:");
                String keyword = scanner.nextLine();
                printBookList(searchBook(keyword));

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
            case "7":
                System.out.printf("Type title:");
                String titleToAddStock = scanner.nextLine().trim();
                System.out.printf("Type stock:");
                int stock = Integer.parseInt(scanner.nextLine().trim());
                addStock(titleToAddStock, stock);
                break;
            case "8":
                printMemberList();
                break;
            case "9":
                System.out.printf("Type user name:");
                String userName = scanner.nextLine().trim();
                System.out.printf("Type email:");
                String email = scanner.nextLine().trim();
                System.out.printf("Type address:");
                String address = scanner.nextLine().trim();
                addMember(userName, email, address);
                break;
            case "10":
                System.out.printf("Type user name:");
                String userNameToWithdraw = scanner.nextLine().trim();
                withdrawMember(userNameToWithdraw);
                break;
            case "11":
                System.out.printf("Type user name:");
                String userNameToModify = scanner.nextLine().trim();
                getMemberByUserName(userNameToModify).ifPresent(member -> {
                    System.out.printf("Type new user name [default:%s] :", member.getUserName());
                    String newUserName = scanner.nextLine().trim();
                    System.out.printf("Type email [default:%s] :", member.getEmail());
                    String newEmail = scanner.nextLine().trim();
                    System.out.printf("Type address [default:%s] :", member.getAddress());
                    String newAddress = scanner.nextLine().trim();
                    modifyMember(userNameToModify, new Member(newUserName, newEmail, newAddress));
                });
                break;
            case "12":
                System.out.printf("Type user name:");
                String userNameToPrintPurchases = scanner.nextLine().trim();
                printPurchaseListByUser(userNameToPrintPurchases);
                break;
            case "s":
                saveAsFile();
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

    private void printPurchaseListByUser(String userName) {
        getPurchaseList().stream()
                .filter(purchase -> purchase.getCustomer().equals(userName))
                .forEach(System.out::println);
    }

    private void saveAsFile() {
        try {
            File tmpFile = new File("memberlist.csv.tmp");
            tmpFile.createNewFile();
            getMemberList().forEach(member -> {
                try {
                    Files.writeString(Path.of("memberlist.csv.tmp"), member.toCsvString() + "\n", StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Files.move(Path.of("memberlist.csv.tmp"), Path.of("memberlist.csv"), StandardCopyOption.REPLACE_EXISTING);
            tmpFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            File bookTmpFile = new File("booklist.csv.tmp");
            bookTmpFile.createNewFile();
            getBookList().forEach(book -> {
                try {
                    Files.writeString(Path.of("booklist.csv.tmp"), book.toCsvString() + "\n", StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Files.move(Path.of("booklist.csv.tmp"), Path.of("booklist.csv"), StandardCopyOption.REPLACE_EXISTING);
            bookTmpFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            File purchaseTmpFile = new File("purchaselist.csv.tmp");
            purchaseTmpFile.createNewFile();
            getPurchaseList().forEach(purchase -> {
                try {
                    Files.writeString(Path.of("purchaselist.csv.tmp"), purchase.toCsvString() + "\n", StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Files.move(Path.of("purchaselist.csv.tmp"), Path.of("purchaselist.csv"), StandardCopyOption.REPLACE_EXISTING);
            purchaseTmpFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void modifyMember(String userNameToModify, Member member) {
        getMemberList().stream()
                .filter(exMember -> exMember.getUserName().equals(userNameToModify))
                .forEach(exMember -> {
                    if (!member.getUserName().isBlank()) {
                        exMember.setUserName(member.getUserName());
                    }

                    if (!member.getEmail().isBlank()) {
                        exMember.setEmail(member.getEmail());
                    }

                    if (!member.getAddress().isBlank()) {
                        exMember.setAddress(member.getAddress());
                    }
                });
    }

    private Optional<Member> getMemberByUserName(String userNameToModify) {
        return getMemberList().stream()
                    .filter(member -> member.getUserName().equals(userNameToModify))
                    .findFirst();
    }

    private void withdrawMember(String userName) {
        getMemberList().stream()
                .filter(member -> member.getUserName().equals(userName))
                .forEach(member -> member.setActive(false));
    }

    private void addMember(String userName, String email, String address) {
        getMemberList().add(new Member(userName, email, address));
    }

    private void printMemberList() {
        getActiveMemberList().stream()
                .forEach(System.out::println);
    }

    private List<Member> getMemberList() {
        return this.memberList;
    }

    private List<Member> getActiveMemberList() {
        return getMemberList().stream().filter(member -> member.isActive()).collect(Collectors.toUnmodifiableList());
    }


    private void addStock(String titleToAddStock, int stock) {
        getBookList().stream()
                .filter(book -> book.getTitle().equals(titleToAddStock))
                .forEach(book -> book.addStock(stock));
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

    private List<Book> searchBook(String keyword) {
        List<Book> bookList = getBookList().stream()
                .filter(book -> book.getTitle().contains(keyword))
                .toList();
        return bookList;
    }

    private void printBookList(List<Book> bookList) {
        printTableLine();
        printHeader();
        bookList.forEach(this::printTable);
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
