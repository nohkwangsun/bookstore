package com.onlinejava.project.bookstore.common.domain.exception;

import java.util.function.Supplier;

public class NoSuchItemException extends ExpectedException {
    public NoSuchItemException(TooManyItemsException.Term term, String value) {
        super(String.format("The %s [%s] do not exist", term.forMessage, value));
    }

    public NoSuchItemException(String message) {
        super(message);
    }

    public static Supplier<NoSuchItemException> supplierOf(TooManyItemsException.Term term, String value) {
        return () -> new NoSuchItemException(term, value);

    }
}
