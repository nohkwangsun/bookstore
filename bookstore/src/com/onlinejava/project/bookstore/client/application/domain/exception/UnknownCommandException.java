package com.onlinejava.project.bookstore.client.application.domain.exception;

public class UnknownCommandException extends UnexpectedException {
    private final String commandNumber;

    public UnknownCommandException(String commandNumber) {
        super("[" + commandNumber + "] is not a valid command");
        this.commandNumber = commandNumber;
    }

    public String getCommandNumber() {
        return commandNumber;
    }
}
