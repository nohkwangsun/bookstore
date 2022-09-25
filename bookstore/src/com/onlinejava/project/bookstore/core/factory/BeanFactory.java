package com.onlinejava.project.bookstore.core.factory;


import com.onlinejava.project.bookstore.core.cli.CliCommandInterface;
import com.onlinejava.project.bookstore.core.util.reflect.ReflectionUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.onlinejava.project.bookstore.Main.BASE_PACKAGE;
import static com.onlinejava.project.bookstore.core.util.reflect.ReflectionUtils.*;

public final class BeanFactory {
    private static Map<Class, Object> beans = new ConcurrentHashMap<>();
    static {
        getInstance().getBeansInBasePackage().stream()
                .map(getInstance()::findOrCreateInstance)
                .forEach(getInstance()::injectDependency);
    }

    public static final String WORKING_DIRECTORY = "bookstore/out/production/bookstore/";
    private static BeanFactory beanFactory;

    private BeanFactory() {
    }

    public synchronized static BeanFactory getInstance() {
        if (beanFactory == null) {
            beanFactory = new BeanFactory();
        }

        return beanFactory;
    }

    private boolean isNotInterface(Class clazz) {
        return !clazz.isInterface();
    }

    private boolean isNotAbstract(Class clazz) {
        return !Modifier.isAbstract(clazz.getModifiers());
    }

    public <T> T get(Class<T> clazz) {
        T object = findOrCreateInstance(clazz);
        if (object == null) {
            throw new RuntimeException("Not found bean: " + clazz.getName());
        }
        injectDependency(object);
        return object;
    }

    private <T> boolean isInterface(Class<T> clazz) {
        return clazz.isInterface();
    }

    private <T> T createOrGetInstance(Class<T> clazz) {
        if (!isBeanAnnotated(clazz)) {
            throw new RuntimeException("The class is not a Bean :" + clazz.getName());
        }
        if (!beans.containsKey(clazz)) {
            addBean(clazz, ReflectionUtils.newInstance(clazz));
        }

        return (T) beans.get(clazz);
    }

    private <T> void addBean(Class<T> clazz, T value) {
        beans.put(clazz, value);
    }

    public <T> void addBean(Class<? extends T> clazz, T value, boolean override) {
        if (beans.containsKey(clazz) && !override) {
            return;
        }

        beans.put(clazz, value);
        injectDependency(value);
    }


    private <T> void injectDependency(T object) {
        Field[] fields = getFieldsFrom(object.getClass());
        Arrays.stream(fields)
                .filter(this::isInjectAnnotated)
                .filter(field -> isFieldNull(object, field))
                .peek(field -> {
                    Object value = findOrCreateInstance(field.getType());
                    setField(object, field, value);
                })
                .forEach(field -> injectDependency(getField(object, field)));
    }

    private <T> boolean isFieldNull(T object, Field field) {
        return getField(object, field) == null;
    }

    private <T> T findOrCreateInstance(Class<T> type) {
        if (beans.containsKey(type)) {
            return (T) beans.get(type);
        }
        Class<T> target = isInterface(type) ? findImplement(type) : type;
        return (T) createOrGetInstance(target);
    }

    private <T> Class findImplement(Class<T> type) {
        return getClassesInBasePackage().stream()
                .filter(this::canBeInstance)
                .filter(clazz -> type.isAssignableFrom(clazz))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Can not Found the implementation of : " + type.getName()));
    }

    private List<Class> getClassesInBasePackage() {
        File directory = getPackageDirectoryAsFile(BASE_PACKAGE);
        return getClassesFrom(directory);
    }

    private List<Class> getBeansInBasePackage() {
        File directory = getPackageDirectoryAsFile(BASE_PACKAGE);
        return getClassesFrom(directory).stream()
                .filter(this::isBeanAnnotated)
                .filter(this::isNotInterface)
                .filter(this::isNotAbstract)
                .toList();
    }

    private boolean canBeInstance(Class clazz) {
        return isNotAbstract(clazz) && isNotInterface(clazz);
    }

    private List<Class> getClassesFrom(File directory) {
        return streamPackageNamesFrom(BASE_PACKAGE, directory).stream()
                .filter(p -> p.startsWith(BASE_PACKAGE))
                .flatMap(p -> getClassesInPackage(p).stream())
                .toList();
    }

    private File getPackageDirectoryAsFile(String basePackage) {
        String packagePath = basePackage.replaceAll("[.]", "/");
        URI uri = Paths.get(WORKING_DIRECTORY + packagePath).toUri();
        return new File(uri);
    }

    private boolean isInjectAnnotated(Field type) {
        return type.isAnnotationPresent(Inject.class);
    }

    private Field[] getFieldsFrom(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    private <T> boolean isBeanAnnotated(Class<T> clazz) {
        boolean isBeanAnnotated = clazz.isAnnotationPresent(Bean.class);
        if (isBeanAnnotated) {
            return true;
        }
        boolean hasBeanAnnotation = Arrays.stream(clazz.getAnnotations()).anyMatch(a -> findAnnotation(a, Bean.class, new HashSet<>()));
        return hasBeanAnnotation;
    }

    private boolean findAnnotation(Annotation anno, Class<?> target, HashSet<Annotation> visited) {
        if (visited.contains(anno)) {
            return false;
        }
        visited.add(anno);

        if (anno.annotationType() == target) {
            return true;
        }

        return Arrays.stream(anno.annotationType().getAnnotations())
                .anyMatch(a -> findAnnotation(a, target, visited));
    }
}
