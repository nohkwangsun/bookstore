package com.onlinejava.project.bookstore.core.reflect;

import com.onlinejava.project.bookstore.domain.model.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {
    public static <T> Map<String, Method> getMethodsMap(Class<T> clazz) {
        Map<String, Method> methods = new HashMap<>();
        for (Method method : clazz.getDeclaredMethods()) {
            methods.put(method.getName(), method);
        }
        return methods;
    }

    public static <T extends Model> T newInstance(Class<T> clazz) {
        try {
            return (T) clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}