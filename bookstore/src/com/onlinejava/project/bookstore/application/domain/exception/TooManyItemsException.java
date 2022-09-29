package com.onlinejava.project.bookstore.application.domain.exception;

public class TooManyItemsException extends ExpectedException {
    public enum Term {
        BookTitle("books titled"),
        MemberName("members named"),
        None("items");

        public final String forMessage;
        Term(String forMessage) {
            this.forMessage = forMessage;
        }
    }
    public TooManyItemsException(Term kind, String value) {
        super(String.format("There are too many %s [%s]", kind.forMessage, value));
    }
    public TooManyItemsException(String message) {
        super(message);
    }
}
