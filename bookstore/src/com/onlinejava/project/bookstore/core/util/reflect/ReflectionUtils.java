package com.onlinejava.project.bookstore.core.util.reflect;

import com.onlinejava.project.bookstore.core.function.Consumers;
import com.onlinejava.project.bookstore.core.function.Functions;
import com.onlinejava.project.bookstore.core.function.Functions.ThrowableFunction;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ReflectionUtils {

    public static <T> Map<String, Method> getMethodsMap(Class<T> clazz) {
        Map<String, Method> methods = new HashMap<>();
        for (Method method : clazz.getDeclaredMethods()) {
            methods.put(method.getName(), method);
        }
        return methods;
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object newInstanceFromMethod(Method method) {
        try {
            return method.getDeclaringClass().getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<String> streamPackageNamesFrom(String packageName, File dir) {
        if (!dir.isDirectory() || dir.listFiles().length <= 0) {
            return List.of();
        }

        List<String> subDirs = Arrays.stream(dir.listFiles())
                .flatMap(f -> streamPackageNamesFrom(packageName + "." + f.getName(), f).stream())
                .collect(Collectors.toList());
        subDirs.add(packageName);
        return subDirs;
    }

    public static List<Class> getClassesInPackage(String basePackage) {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(basePackage.replaceAll("[.]", "/"));
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);

        try (is; isr; reader) {
            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(Functions.unchecked(line -> Class.forName(basePackage + "." + line.substring(0, line.length() - 6))))
                    .collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void setField(T object, Field field, Object value) {
        accessField(object, field, f -> {
            f.set(object, value);
        });
    }


    public static <T, F> F getField(T object, Field field) {
        return (F) getField(object, field.getName(), field.getType());
    }

    public static <T, F> F getField(T object, String fieldName, Class<F> fieldType) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            return accessField(object, field, f -> (F) f.get(object));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void accessField(T object, Field field, Consumers.ThrowableConsumer<Field> consumer) {
        accessField(object, field, f -> {
            consumer.accpet(f);
            return null;
        });
    }

    private static <T, F> F accessField(T object, Field field, ThrowableFunction<Field,F> function) {
        boolean accessible = field.canAccess(object);
        field.setAccessible(true);
        F value = null;
        try {
            value = function.apply(field);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(accessible);
        }
        return value;
    }
}
