package com.onlinejava.project.bookstore.core.cli;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.application.domain.exception.BookStoreException;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;
import com.onlinejava.project.bookstore.core.function.Functions;
import com.onlinejava.project.bookstore.core.util.reflect.ReflectionUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandCache {
    private static Map<String, CliCommandInterface> commands = new HashMap<>();

    public static void addCommands(Map<String, CliCommandInterface> commands) {
        CommandCache.commands.putAll(commands);
    }

    public static Collection<CliCommandInterface> getCommands() {
        return CommandCache.commands.values();
    }

    public static Optional<CliCommandInterface> getCommand(String command) {
        return Optional.ofNullable(CommandCache.commands.get(command));
    }

    public static void loadCommands() {
        List<Class> classesInPackage = getClassesFromBasePackage(Main.BASE_PACKAGE);

        Stream<CliCommandInterface> cliCommandInterfaces = newInstancesOfCliCommandInterfaces(classesInPackage);
        Stream<CliCommandInterface> annotatedCommands = newInstancesOfAnnotatedCommands(classesInPackage);

        Map<String, CliCommandInterface> commands = Stream.concat(cliCommandInterfaces, annotatedCommands)
                    .map(CommandCache::commandToProxy)
                    .collect(Collectors.toMap(CliCommandInterface::getCommandID, Function.identity()));

        CommandCache.addCommands(commands);
    }

    private static Stream<CliCommandInterface> newInstancesOfAnnotatedCommands(List<Class> classesInPackage) {
        Stream<CliCommandInterface> annotatedCommandStream = classesInPackage.stream()
                    .filter(clazz -> clazz.isAnnotationPresent(CliCommand.class))
                    .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
                    .filter(method -> method.isAnnotationPresent(CliCommand.class))
                    .filter(method -> !method.isAnnotationPresent(Deprecated.class))
                    .filter(method -> method.getParameterCount() == 0)
                    .map(CommandCache::methodToCliCommand);
        return annotatedCommandStream;
    }

    private static Stream<CliCommandInterface> newInstancesOfCliCommandInterfaces(List<Class> classesInPackage) {
        Stream<CliCommandInterface> cliCommandInterfaceStream = classesInPackage.stream()
                .filter(clazz -> CliCommandInterface.class.isAssignableFrom(clazz))
                .filter(clazz -> !clazz.isInterface())
                .filter(clazz -> !clazz.isAnonymousClass())
                .filter(clazz -> !clazz.isAnnotationPresent(Deprecated.class))
                .map(Functions.unchecked(clazz -> (CliCommandInterface) clazz.getDeclaredConstructor().newInstance()));
        return cliCommandInterfaceStream;
    }

    private static List<Class> getClassesFromBasePackage(String basePackage) {
        File dir = convertPackageNameToFile(basePackage);
        return streamPackageNamesFrom(basePackage, dir).stream()
                .filter(p -> p.startsWith(basePackage))
                .flatMap(p -> getClassesInPackage(p).stream())
                .toList();
    }

    private static File convertPackageNameToFile(String basePackage) {
        URL resource = ClassLoader.getSystemClassLoader().getResource(basePackage.replaceAll("[.]", "/"));
        return new File(resource.getFile());
    }

    private static List<String> streamPackageNamesFrom(String packageName, File dir) {
        if (!dir.isDirectory() || dir.listFiles().length <= 0) {
            return List.of();
        }

        List<String> subDirs = Arrays.stream(dir.listFiles())
                .flatMap(f -> streamPackageNamesFrom(packageName + "." + f.getName(), f).stream())
                .collect(Collectors.toList());
        subDirs.add(packageName);
        return subDirs;
    }

    private static List<Class> getClassesInPackage(String basePackage) {
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

    private static CliCommandInterface commandToProxy(CliCommandInterface cmd) {
        ClassLoader classLoader = CliCommandInterface.class.getClassLoader();
        Class[] interfaces = {CliCommandInterface.class};
        CommandInvocationHandler handler = new CommandInvocationHandler(cmd);
        return (CliCommandInterface) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }

    private static CliCommandInterface methodToCliCommand(Method method) {
        CliCommand classCommand = method.getClass().getDeclaredAnnotation(CliCommand.class);
        CliCommand methodCommand = method.getDeclaredAnnotation(CliCommand.class);
        final Object instance = ReflectionUtils.newInstanceFromMethod(method);

        return new CliCommandInterface() {
            @Override
            public String getCommandID() {
                return methodCommand.ID().isBlank() ? classCommand.ID() : methodCommand.ID();
            }

            @Override
            public String getTitle() {
                return methodCommand.title().isBlank() ? classCommand.title() : methodCommand.title();
            }

            @Override
            public String getDescription() {
                return methodCommand.description().isBlank() ? classCommand.description() : methodCommand.description();
            }

            @Override
            public int order() {
                return methodCommand.order();
            }

            @Override
            public void run() {
                ReflectionUtils.invoke(method, instance);
            }
        };
    }


}
