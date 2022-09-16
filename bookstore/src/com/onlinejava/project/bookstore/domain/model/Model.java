package com.onlinejava.project.bookstore.domain.model;


import com.onlinejava.project.bookstore.core.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.onlinejava.project.bookstore.core.Functions.unchecked;

public abstract class Model {
    private String toGetterName(Field f) {
        String prefix = f.getType().equals(boolean.class) ? "is" : "get";
        return prefix + StringUtils.toCapitalize(f.getName());
    }

    public String toCsvString() {
        Field[] fields = Member.class.getDeclaredFields();
        return Arrays.stream(fields)
                .map(this::toGetterName)
                .map(unchecked(Member.class::getMethod))
                .map(unchecked(method -> method.invoke(this)))
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
