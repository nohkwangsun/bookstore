package com.onlinejava.project.bookstore.core.function;

import java.util.function.Function;

public class Functions {
    @FunctionalInterface
    public interface ThrowableFunction<T, R> {
        R apply(T t) throws Throwable;

    }

    public static <T, R> java.util.function.Function<T, R> unchecked(ThrowableFunction<T, R> function) {
        return (T t) -> {
            try {
                return function.apply(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}
