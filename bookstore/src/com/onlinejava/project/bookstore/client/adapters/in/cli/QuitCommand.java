package com.onlinejava.project.bookstore.client.adapters.in.cli;

import com.onlinejava.project.bookstore.core.cli.CliCommandInterface;

@SuppressWarnings({"unused"})
public class QuitCommand implements CliCommandInterface {
    @Override
    public String getCommandID() {
        return "q";
    }

    @Override
    public String getTitle() {
        return "Quit";
    }

    @Override
    public String getDescription() {
        return "Exit the program.";
    }

    @Override
    public void run() {
        System.exit(0);
    }
}
