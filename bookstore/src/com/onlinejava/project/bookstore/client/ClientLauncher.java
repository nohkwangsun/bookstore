package com.onlinejava.project.bookstore.client;


import com.onlinejava.project.bookstore.client.application.domain.ClientApplication;

public class ClientLauncher {

    public static final boolean HAS_HEADER = true;
    public static final String BASE_PACKAGE = "com.onlinejava.project.bookstore.client";

    public static void main(String[] args) {
        ClientApplication application = new ClientApplication();
        application.login();
        application.run();
    }
}
