package com.onlinejava.project.bookstore.common.domain.entity;


import com.onlinejava.project.bookstore.core.util.reflect.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.onlinejava.project.bookstore.core.function.Functions.unchecked;

public abstract class Entity {

    public static String toCsvHeader(Class<? extends Entity> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .map(field -> field.getName())
                .collect(Collectors.joining(", "));
    }

    public String toCsvString() {
        return Arrays.stream(ReflectionUtils.getDeclaredGetters(this.getClass()))
                .map(unchecked(getter -> getter.invoke(this)))
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }



}
