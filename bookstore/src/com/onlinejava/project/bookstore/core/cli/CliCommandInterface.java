package com.onlinejava.project.bookstore.core.cli;

import com.onlinejava.project.bookstore.BookStore;

import java.util.Scanner;
import java.util.regex.Pattern;

public interface CliCommandInterface {
    Scanner scanner = new Scanner(System.in);
    BookStore bookstore = new BookStore();

    String getCommandID();

    String getTitle();

    String getDescription();

    void run();

    default int order() {
        return Integer.MAX_VALUE;
    }

    static int ordering(CliCommandInterface c1, CliCommandInterface c2) {
        if (c1.order() != c2.order()) {
            return c1.order() - c2.order();
        }

        boolean isC1Number = Pattern.compile("\\d+").matcher(c1.getCommandID()).matches();
        boolean isC2Number = Pattern.compile("\\d+").matcher(c2.getCommandID()).matches();
        if (isC1Number && isC2Number) {
            return Integer.parseInt(c1.getCommandID()) - Integer.parseInt(c2.getCommandID());
        } else if (isC1Number) {
            return -1;
        } else if (isC2Number) {
            return 1;
        } else {
            return c1.getCommandID().compareTo(c2.getCommandID());
        }
    }
}
