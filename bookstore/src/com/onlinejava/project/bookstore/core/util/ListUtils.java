package com.onlinejava.project.bookstore.core.util;

import com.onlinejava.project.bookstore.adapters.cli.exception.EmptyItemListException;
import com.onlinejava.project.bookstore.application.domain.entity.Book;

import java.util.List;

public class ListUtils {

    public static boolean isEmpty(List<Book> list) {
        return list == null || list.isEmpty();
    }
    public static boolean isNotOnlyOne(List<Book> list) {
        return list == null || list.size() != 1;
    }
}
