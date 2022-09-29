package com.onlinejava.project.bookstore.core.util;

public class ExceptionUtils {
    public static Throwable getRootCause(Throwable e) {
        return e.getCause() == null ? e : getRootCause(e.getCause());
    }
}
