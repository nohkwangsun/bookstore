package com.onlinejava.project.bookstore.core.util.validator;

import com.onlinejava.project.bookstore.adapters.cli.exception.EmptyItemListException;

import java.util.List;

public class Validators {
    public static <T> void throwIfEmpty(List<T> list, Class<T> clazz) {
        if (list == null || list.isEmpty()) {
            throw new EmptyItemListException(clazz);
        }
    }
    public static <T> void throwIfEmpty(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new EmptyItemListException();
        }
    }
}
