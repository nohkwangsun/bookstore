package com.onlinejava.project.bookstore.domain.entity;


import com.onlinejava.project.bookstore.core.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.onlinejava.project.bookstore.core.function.Functions.unchecked;

public abstract class Entity {

    private String toGetterName(Field f) {
        String prefix = f.getType().equals(boolean.class) ? "is" : "get";
        return prefix + StringUtils.toCapitalize(f.getName());
    }

    public static String toCsvHeader(Class<? extends Entity> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .map(field -> field.getName())
                .collect(Collectors.joining(", "));
    }
    public String toCsvString() {
        Field[] fields = this.getClass().getDeclaredFields();
        return Arrays.stream(fields)
                .map(this::toGetterName)
                .map(unchecked(getterName -> this.getClass().getMethod(getterName)))
                .map(unchecked(getter -> getter.invoke(this)))
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

}
