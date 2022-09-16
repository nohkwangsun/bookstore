package com.onlinejava.project.bookstore.core.function;

public class Consumers {
    @FunctionalInterface
    public interface ThrowableConsumer<T> {
        void accpet(T t) throws Throwable;

    }

    public static <T> java.util.function.Consumer<T> unchecked(ThrowableConsumer<T> consumer) {
        return (T t) -> {
            try {
                consumer.accpet(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}
