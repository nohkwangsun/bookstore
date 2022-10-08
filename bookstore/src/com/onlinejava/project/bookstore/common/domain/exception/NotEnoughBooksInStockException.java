package com.onlinejava.project.bookstore.common.domain.exception;

import com.onlinejava.project.bookstore.common.domain.exception.TooManyItemsException.Term;

public class NotEnoughBooksInStockException extends ExpectedException {
    public NotEnoughBooksInStockException(Term term, String value) {
        super(String.format("The %s [%s] are not enough in stock", term.forMessage, value));
    }
    public NotEnoughBooksInStockException(String message) {
        super(message);
    }
}
