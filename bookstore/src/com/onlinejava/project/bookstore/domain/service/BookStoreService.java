package com.onlinejava.project.bookstore.domain.service;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.core.cli.CliCommandInterface;
import com.onlinejava.project.bookstore.core.cli.CommandInvocationHandler;
import com.onlinejava.project.bookstore.core.function.Consumers;
import com.onlinejava.project.bookstore.core.function.Functions;
import com.onlinejava.project.bookstore.core.reflect.ModelSetter;
import com.onlinejava.project.bookstore.domain.model.Member;
import com.onlinejava.project.bookstore.domain.model.*;

import java.io.*;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingInt;

public class BookStoreService {
    private List<Book> bookList;
    private List<Purchase> purchaseList;
    private List<Member> memberList;
    private Map<String, CliCommandInterface> commands;


    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    {

        try (
                InputStream resourceIStream = ClassLoader.getSystemClassLoader().getResourceAsStream(Main.COMMAND_PACKAGE.replaceAll("[.]", "/"));
                InputStreamReader resourceISR = new InputStreamReader(resourceIStream);
                BufferedReader resourceReader = new BufferedReader(resourceISR)
        ) {

            List<Class> classesInPackage = resourceReader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(Functions.unchecked(line -> Class.forName(Main.COMMAND_PACKAGE + "." + line.substring(0, line.length() - 6))))
                    .collect(Collectors.toUnmodifiableList());

            Stream<CliCommandInterface> cliCommandInterfaceStream = classesInPackage.stream()
                    .filter(clazz -> CliCommandInterface.class.isAssignableFrom(clazz))
                    .filter(clazz -> !clazz.isInterface())
                    .map(Functions.unchecked(clazz -> (CliCommandInterface) clazz.getDeclaredConstructor().newInstance()));

            Stream<CliCommandInterface> annotatedCommandStream = classesInPackage.stream()
                    .filter(clazz -> clazz.isAnnotationPresent(CliCommand.class))
                    .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
                    .filter(method -> method.isAnnotationPresent(CliCommand.class))
                    .filter(method -> method.getParameterCount() == 0)
                    .map(BookStoreService::methodToCliCommand);

            commands = Stream.concat(cliCommandInterfaceStream, annotatedCommandStream)
                    .map(BookStoreService::commandToProxy)
                    .collect(Collectors.toMap(CliCommandInterface::getCommandID, Function.identity()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T extends Model> List<T> getModelListFromLines(String filePath, Class<T> clazz) {
        List<String> lines = getFileLines(filePath).toList();
        return getModelListFromLines(lines, clazz, Main.HAS_HEADER);
    }

    public <T extends Model> List<T> getModelListFromLines(List<String> lines, Class<T> clazz, boolean hasHeader) {
        return hasHeader
                ? getModelListFromLinesWithHeader(lines, clazz)
                : getModelListFromLinesWithoutHeader(lines, clazz);
    }

    public <T extends Model> List<T> getModelListFromLinesWithHeader(List<String> lines, Class<T> clazz) {
        if (lines.size() <= 1) {
            return Collections.emptyList();
        }

        String[] headers = lines.get(0).split(",");
        return lines.stream().skip(1)
                .map(line -> {

                    ModelSetter<T> ObjectSetter = new ModelSetter(clazz);
                    String[] values = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
                    for (int i = 0; i < headers.length; i++) {
                        ObjectSetter.set(headers[i], values[i]);
                    }

                    return ObjectSetter.getObject();
                })
                .collect(Collectors.toList());
    }

    public <T extends Model> List<T> getModelListFromLinesWithoutHeader(List<String> lines, Class<T> clazz) {
        return lines.stream()
                .map(line -> {

                    ModelSetter<T> ObjectSetter = new ModelSetter(clazz);
                    Field[] fields = clazz.getDeclaredFields();
                    String[] values = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
                    for (int i = 0; i < fields.length && i < values.length; i++) {
                        ObjectSetter.set(fields[i].getName(), values[i]);
                    }

                    return ObjectSetter.getObject();
                })
                .collect(Collectors.toList());
    }

    private Stream<String> getFileLines(String first) {
        try {
            return Files.lines(Path.of(first));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static CliCommandInterface commandToProxy(CliCommandInterface cmd) {
        ClassLoader classLoader = CliCommandInterface.class.getClassLoader();
        Class[] interfaces = {CliCommandInterface.class};
        CommandInvocationHandler handler = new CommandInvocationHandler(cmd);
        return (CliCommandInterface) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }

    private static CliCommandInterface methodToCliCommand(Method method) {
        CliCommand classCommand = method.getClass().getDeclaredAnnotation(CliCommand.class);
        CliCommand methodCommand = method.getDeclaredAnnotation(CliCommand.class);

        Object instance = null;
        try {
            instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        final Object finalInstance = instance;

        return new CliCommandInterface() {
            @Override
            public String getCommandID() {
                return methodCommand.ID().isBlank() ? classCommand.ID() : methodCommand.ID();
            }

            @Override
            public String getTitle() {
                return methodCommand.title().isBlank() ? classCommand.title() : methodCommand.title();
            }

            @Override
            public String getDescription() {
                return methodCommand.description().isBlank() ? classCommand.description() : methodCommand.description();
            }

            @Override
            public int order() {
                return methodCommand.order();
            }

            @Override
            public void run() {
                try {
                    method.invoke(finalInstance);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        };
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

        commands.values().stream()
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

    public void runCommand(Scanner scanner) {
        String cmdNum = scanner.nextLine().trim();
        Optional.ofNullable(commands.get(cmdNum)).ifPresentOrElse(
                CliCommandInterface::run,
                () -> System.out.println("Error: Unknown command : " + cmdNum)
        );

    }

    public void printPurchaseListByUser(String userName) {
        getPurchaseList().stream()
                .filter(purchase -> purchase.getCustomer().equals(userName))
                .forEach(System.out::println);
    }

    public void saveAsFile() {
        try {
            File tmpFile = new File("memberlist.csv.tmp");
            tmpFile.createNewFile();

            if (Main.HAS_HEADER) {
                Files.writeString(Path.of("memberlist.csv.tmp"), Model.toCsvHeader(Member.class) + "\n", StandardOpenOption.APPEND);
            }
            getMemberList().forEach(Consumers.unchecked((Member member) -> {
                    Files.writeString(Path.of("memberlist.csv.tmp"), member.toCsvString() + "\n", StandardOpenOption.APPEND);
            }));

            Files.move(Path.of("memberlist.csv.tmp"), Path.of("memberlist.csv"), StandardCopyOption.REPLACE_EXISTING);
            tmpFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            File bookTmpFile = new File("booklist.csv.tmp");
            bookTmpFile.createNewFile();

            if (Main.HAS_HEADER) {
                Files.writeString(Path.of("booklist.csv.tmp"), Model.toCsvHeader(Book.class) + "\n", StandardOpenOption.APPEND);
            }
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

            if (Main.HAS_HEADER) {
                Files.writeString(Path.of("purchaselist.csv.tmp"), Model.toCsvHeader(Purchase.class) + "\n", StandardOpenOption.APPEND);
            }
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

    public void modifyMember(String userNameToModify, Member member) {
        getMemberByUserName(userNameToModify)
                .ifPresent(exMember -> {
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

    public Optional<Member> getMemberByUserName(String userName) {
        return getMemberList().stream()
                .filter(member -> member.getUserName().equals(userName))
                .findFirst();
    }

    public void withdrawMember(String userName) {
        getMemberByUserName(userName)
                .ifPresent(member -> member.setActive(false));
    }

    public void addMember(String userName, String email, String address) {
        Member member = new Member();
        member.setUserName(userName);
        member.setEmail(email);
        member.setAddress(address);
        member.setTotalPoint(0);
        member.setGrade(Grade.BRONZE);
        member.setActive(true);
        getMemberList().add(member);
    }

    public void printMemberList() {
        getActiveMemberList().forEach(System.out::println);
    }

    private List<Member> getMemberList() {
        return this.memberList;
    }

    private List<Member> getActiveMemberList() {
        return getMemberList().stream().filter(Member::isActive).toList();
    }


    public void addStock(String titleToAddStock, int stock) {
        getBookList().stream()
                .filter(book -> book.getTitle().equals(titleToAddStock))
                .forEach(book -> book.addStock(stock));
    }

    public void printPurchaseList() {
        getPurchaseList().forEach(System.out::println);
    }

    public void buyBook(String titleToBuy, String customer) {
        getBookList().stream()
                .filter(book -> book.getTitle().equals(titleToBuy))
                .filter(book -> book.getStock() > 0)
                .forEach(book -> {
                    book.setStock(book.getStock() - 1);
                    Purchase purchase = new Purchase();
                    purchase.setTitle(titleToBuy);
                    purchase.setCustomer(customer);
                    purchase.setNumberOfPurchase(1);
                    purchase.setTotalPrice(book.getPrice());
                    purchase.setPoint(getPoint(book, customer));
                    getPurchaseList().add(purchase);
                    getMemberByUserName(customer)
                            .ifPresent(member -> member.addPoint(getPoint(book, customer)));
                });
    }

    private int getPoint(Book book, String customer) {
        return getMemberByUserName(customer)
                .map(m -> getPointByMember(book, m))
                .orElseThrow();
    }

    private int getPointByMember(Book book, Member member) {
        return member.getGrade().calculatePoint(book.getPrice());
    }

    private List<Purchase> getPurchaseList() {
        return this.purchaseList;
    }

    public void deleteBook(String deletingTitle) {
        getBookList().stream()
                .filter(book -> book.getTitle().equals(deletingTitle))
                .findFirst()
                .ifPresent(getBookList()::remove);
    }

    public void createBook(Book newBook) {
        getBookList().add(newBook);
    }

    public List<Book> searchBook(String keyword) {
        return getBookList().stream()
                .filter(book -> book.getTitle().contains(keyword))
                .toList();
    }

    public void printBookList(List<Book> bookList) {
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
        System.out.printf("| %-10s \t | %-10s \t | %-10s \t | %-10s \t | %-10s \t |\n",
                book.getTitle(), book.getWriter(), book.getPrice(), book.getLocation(), book.getStock());
    }

    private void printTableLine() {
        IntStream.range(1, 60).forEach(i -> System.out.print("-"));
        System.out.println();
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void updateMemberGrades() {
        getPurchaseList().stream()
                .collect(groupingBy(Purchase::getCustomer, summarizingInt(Purchase::getTotalPrice)))
                .forEach((user, stat) -> {
                    Grade newGrade = Grade.getGradeByTotalPrice(stat.getSum());
                    getMemberByUserName(user).ifPresent(member -> member.setGrade(newGrade));
                });

    }
}