package com.onlinejava.project.bookstore;

import com.onlinejava.project.bookstore.application.domain.BookStoreApplication;

public class Main {

    public static final boolean HAS_HEADER = true;
    public static final String BASE_PACKAGE = "com.onlinejava.project.bookstore";

    public static void main(String[] args) {
        new BookStoreApplication().run();
    }
}
