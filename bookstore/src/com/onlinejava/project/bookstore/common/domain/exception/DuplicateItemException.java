package com.onlinejava.project.bookstore.common.domain.exception;

import java.util.function.Consumer;

public class DuplicateItemException extends ExpectedException {
    public DuplicateItemException(TooManyItemsException.Term term, String value) {
        super(String.format("The %s [%s] already exist", term.forMessage, value));
    }
    public DuplicateItemException(String message) {
        super(message);
    }

    public static Consumer consumerOf(TooManyItemsException.Term term, String value) {
        return i -> {
            throw new DuplicateItemException(term, value);
        };
    }
}
