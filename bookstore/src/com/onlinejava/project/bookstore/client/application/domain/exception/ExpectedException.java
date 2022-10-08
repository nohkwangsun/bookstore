package com.onlinejava.project.bookstore.client.application.domain.exception;

public class ExpectedException extends BookStoreException {

    public ExpectedException() {
        super();
    }

    public ExpectedException(String message) {
        super(message);
    }

    public ExpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpectedException(Throwable cause) {
        super(cause);
    }
}
