package com.onlinejava.project.bookstore.application.domain.exception;

public class UnexpectedException extends BookStoreException {

    public UnexpectedException() {
        super();
    }

    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedException(Throwable cause) {
        super(cause);
    }

    public static void throwIfFailed(boolean result, String message) {
        if (!result) {
            throw new UnexpectedException(message);
        }
    }
}
