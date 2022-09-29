package com.onlinejava.project.bookstore.adapters.cli.exception;

import com.onlinejava.project.bookstore.application.domain.exception.ExpectedException;

public class EmptyItemListException extends ExpectedException {
    private final Class<?> clazz;

    public <T> EmptyItemListException(Class<T> clazz) {
        super(clazz.getSimpleName() + " list is empty");
        this.clazz = clazz;
    }

    public <T> EmptyItemListException() {
        super("list is empty");
        this.clazz = Object.class;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getClassName() {
        return clazz.getName();
    }
}
