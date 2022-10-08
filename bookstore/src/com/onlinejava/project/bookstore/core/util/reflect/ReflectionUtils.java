package com.onlinejava.project.bookstore.core.util.reflect;

import com.onlinejava.project.bookstore.application.domain.exception.BookStoreException;
import com.onlinejava.project.bookstore.core.function.Consumers;
import com.onlinejava.project.bookstore.core.function.Functions;
import com.onlinejava.project.bookstore.core.function.Functions.ThrowableFunction;
import com.onlinejava.project.bookstore.core.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.onlinejava.project.bookstore.core.function.Functions.unchecked;


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

    public static <T> void invokeSetter(T object, String fieldName, Class<?> fieldType, Object value) {
        String setterName = "set" + StringUtils.toCapitalize(fieldName);
        try {
            object.getClass().getDeclaredMethod(setterName, fieldType).invoke(object, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
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

    private static String toGetterName(Field f) {
        String prefix = f.getType().equals(boolean.class) ? "is" : "get";
        return prefix + StringUtils.toCapitalize(f.getName());
    }

    public static Method[] getDeclaredGetters(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .map(ReflectionUtils::toGetterName)
                .filter(getterName -> hasMethod(clazz, getterName))
                .map(unchecked(getterName -> clazz.getMethod(getterName)))
                .toArray(Method[]::new);
    }

    private static boolean hasMethod(Class<?> clazz, String methodName) {
        try {
            clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    public static <T> T invokeMethod(Object object, String methodName, Class<T> returnType, Object ... args) {
        try {
            Method method = object.getClass().getDeclaredMethod(methodName, getClasses(args));
            boolean accessible = method.canAccess(object);
            method.setAccessible(true);
            T result = (T) method.invoke(object, args);
            method.setAccessible(accessible);
            return result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?>[] getClasses(Object[] objects) {
        return Arrays.stream(objects).map(Object::getClass).toArray(Class<?>[]::new);
    }

    public static Object invoke(Method method, Object instance, Object ... args) {
        try {
            return method.invoke(instance, args );
        } catch (IllegalAccessException | InvocationTargetException e) {
            if (e.getCause() instanceof BookStoreException) {
                throw (BookStoreException) e.getCause();
            }
            throw new RuntimeException(e);
        }
    }

    public static Class<?> getFieldType(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> getFieldType(Object object, String fieldName) {
        return getFieldType(object.getClass(), fieldName);
    }
}
