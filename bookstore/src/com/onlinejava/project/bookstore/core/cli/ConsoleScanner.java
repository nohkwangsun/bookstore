package com.onlinejava.project.bookstore.core.cli;

import java.util.Scanner;
import java.util.function.Function;


public class ConsoleScanner {
    public static final Scanner scanner = new Scanner(System.in);

    public static String prompt(String itemName) {
        System.out.print("Type " + itemName + ":");
        return scanner.nextLine().trim();
    }
    public static String promptDefaultValue(String itemName, String defaultValue) {
        System.out.print("Type " + itemName + " [default:" + defaultValue + "] :");
        String input = scanner.nextLine().trim();
        return input.isBlank() ? defaultValue : input;
    }
    public static <R> R prompt(String itemName, Function<String, R> resolve) {
        System.out.print("Type " + itemName + ":");
        return resolve.apply(scanner.nextLine().trim());
    }

    public static boolean promptYN(String question) {
        System.out.print(question + " [y(default) / n]:");
        String yn = scanner.nextLine().trim();
        return "Y".equalsIgnoreCase(yn);
    }
}
