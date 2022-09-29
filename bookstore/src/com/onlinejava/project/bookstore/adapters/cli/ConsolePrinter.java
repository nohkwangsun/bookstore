package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.domain.entity.Entity;
import com.onlinejava.project.bookstore.core.function.Functions;
import com.onlinejava.project.bookstore.core.util.reflect.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;

public class ConsolePrinter<T extends Entity> {
    public static final int COLUMN_SIZE = 18;
    private final int lineSize;
    private final List<StringMethodPair> getters;
    private final Class<T> clazz;

    public ConsolePrinter(Class<T> clazz) {
        this.clazz = clazz;
        this.getters = Arrays.stream(ReflectionUtils.getDeclaredGetters(clazz))
                .map(getter -> StringMethodPair.of(getFormatByType(getter), getter))
                .collect(Collectors.toList());
        this.lineSize = 1 + (2 + COLUMN_SIZE + 1) * getters.size() + 1;

    }

    private static String convertGetterToFieldName(Method method) {
        return method.getName().substring(3).toUpperCase();
    }

    private String getFormatByType(Method getter) {
        return switch (getter.getReturnType().getName()) {
            case "long", "int" : yield "d";
            case "double", "float" : yield "f";
            default : yield "s";
        };
    }

    private void printHeader() {
        String format = makeFormat("s");
        Object[] data = makeHeaderData();
        System.out.printf(format, data);
        printLine();
    }

    private Object[] makeHeaderData() {
        return getters.stream().map(i -> i._2).map(ConsolePrinter::convertGetterToFieldName).collect(Collectors.toList()).toArray();
    }

    private Object[] makeRowData(T object) {
        return getters.stream().map(Functions.unchecked(i -> i._2.invoke(object))).toArray();
    }

    private String makeFormat(String fixed) {
        return getters.stream().map(i -> "  %" + "-" + COLUMN_SIZE + fixed).collect(Collectors.joining("|", "|", "|\n"));
    }

    private String makeFormat() {
        return getters.stream().map(i -> "  %" + "-" + COLUMN_SIZE + i._1).collect(Collectors.joining("|", "|", "|\n"));
    }

    private void printRow(T book) {
        String format = makeFormat();
        Object[] data = makeRowData(book);
        System.out.printf(format, data);
        printLine();
    }

    private void printLine() {
        IntStream.range(1, lineSize).forEach(i -> System.out.print("-"));
        System.out.println();
    }

    public void printList(List<T> list) {
        printLine();
        printHeader();
        list.forEach(this::printRow);
    }
}
