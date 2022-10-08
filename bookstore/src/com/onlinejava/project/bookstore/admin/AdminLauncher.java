package com.onlinejava.project.bookstore.admin;

import com.onlinejava.project.bookstore.admin.application.domain.BookStoreApplication;

public class AdminLauncher {

    public static final boolean HAS_HEADER = true;
    public static final String BASE_PACKAGE = "com.onlinejava.project.bookstore.admin";

    public static void main(String[] args) {
        new BookStoreApplication().run();
    }
}
