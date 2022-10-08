package com.onlinejava.project.bookstore.core.util;

import java.lang.reflect.Field;

public class StringUtils {
    public static String toCapitalize(String string) {
        String first = string.substring(0, 1).toUpperCase();
        String tail = string.substring(1);
        return first + tail;
    }

    public static String camel(String string) {
        char[] from = string.toLowerCase().toCharArray();
        char[] to = new char[from.length];
        boolean underScore = false;
        int t = 0;
        for (int f = 0; f < from.length; f++) {
            if (from[f] == '_') {
                underScore = true;
                continue;
            }
            to[t++] = underScore ? Character.toUpperCase(from[f]) : from[f];
            underScore = false;
        }
        return String.valueOf(to, 0, t);
    }
}
