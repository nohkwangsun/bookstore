package com.onlinejava.project.bookstore.core.function;

import java.util.function.Function;

public class Functions {
    @FunctionalInterface
    public interface ThrowableFunction<T, R> {
        R apply(T t) throws Throwable;

    }
    @FunctionalInterface
    public interface ThrowableBiFunction<T, U, R> {
        R apply(T t, U u) throws Throwable;
    }

    public static <T, U, R> java.util.function.BiFunction<T, U, R> unchecked(ThrowableBiFunction<T, U, R> function) {
        return (T t, U u) -> {
            try {
                return function.apply(t, u);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
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
