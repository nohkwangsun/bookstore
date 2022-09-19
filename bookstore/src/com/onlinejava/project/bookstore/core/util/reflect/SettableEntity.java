package com.onlinejava.project.bookstore.core.util.reflect;

import com.onlinejava.project.bookstore.core.util.StringUtils;
import com.onlinejava.project.bookstore.domain.entity.Grade;
import com.onlinejava.project.bookstore.domain.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Supplier;

public class SettableEntity<T extends Entity> {
    private final T entity;
    private final Map<String, Method> methods;

    public SettableEntity(T entity) {
        this.entity = entity;
        this.methods = ReflectionUtils.getMethodsMap(entity.getClass());
    }
    public SettableEntity(Class<T> clazz) {
        this.entity = ReflectionUtils.newInstance(clazz);
        this.methods = ReflectionUtils.getMethodsMap(entity.getClass());
    }

    public void set(String fieldName, String value) {
        String setterName = "set" + StringUtils.toCapitalize(fieldName.trim());
        Method setter = methods.get(setterName);
        if (setter == null) {
            System.out.printf("Setter method of [%s] is not found. [value:%s]\n", fieldName, value);
            return;
        }
        set(setter, value);
    }

    public void set(Method setter, String value) {
        if (setter == null) {
            System.out.printf("Setter method is null. [value:%s]\n", value);
            return;
        }
        if (setter.getParameterCount() != 1) {
            System.out.printf("Setter method [%s] has invalid number of parameters. [#params:%d]\n", setter.getName(), setter.getParameterCount());
            return;
        }

        Type type = setter.getParameters()[0].getType();
        invoke(setter, valueConverter(type, value.trim()));
    }

    private Object valueConverter(Type type, String value) {
        if (type == int.class) {
            return Integer.valueOf(value);

        } else if (type == boolean.class) {
            return Boolean.valueOf(value);

        } else if (type == Grade.class) {
            return Grade.valueOf(value);

        } else if (type == String.class) {
            return value;

        } else {
            throw new IllegalArgumentException(
                    String.format("%s can not be assigned to type [%s] in %s class",
                            value, type.getTypeName(), entity.getClass().getName())
            );
        }
    }

    private Object invoke(Method setter, Object value) {
        try {
            return setter.invoke(entity, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public T getOrElse(Supplier<T> supplier) {
        if (entity == null) {
            return supplier.get();
        }
        return entity;
    }

    public T getOrThrow() {
        if (entity == null) {
            throw new RuntimeException("Entity is null");
        }
        return entity;
    }

    public T getObject() {
        return entity;
    }
}
