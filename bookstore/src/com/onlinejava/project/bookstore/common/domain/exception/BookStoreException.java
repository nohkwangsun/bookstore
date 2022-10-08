package com.onlinejava.project.bookstore.common.domain.exception;

public class BookStoreException extends RuntimeException {

    public BookStoreException() {
        super();
    }

    public BookStoreException(String message) {
        super(message);
    }

    public BookStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookStoreException(Throwable cause) {
        super(cause);
    }
}
