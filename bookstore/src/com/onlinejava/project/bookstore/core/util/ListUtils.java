package com.onlinejava.project.bookstore.core.util;

import java.util.List;

public class ListUtils {

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
    public static boolean isNotOnlyOne(List<?> list) {
        return list == null || list.size() != 1;
    }
}
