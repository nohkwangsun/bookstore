package com.onlinejava.project.bookstore.core.cli;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.core.function.Functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
        try (
                InputStream resourceIStream = ClassLoader.getSystemClassLoader().getResourceAsStream(Main.COMMAND_PACKAGE.replaceAll("[.]", "/"));
                InputStreamReader resourceISR = new InputStreamReader(resourceIStream);
                BufferedReader resourceReader = new BufferedReader(resourceISR)
        ) {

            List<Class> classesInPackage = resourceReader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(Functions.unchecked(line -> Class.forName(Main.COMMAND_PACKAGE + "." + line.substring(0, line.length() - 6))))
                    .collect(Collectors.toUnmodifiableList());

            Stream<CliCommandInterface> cliCommandInterfaceStream = classesInPackage.stream()
                    .filter(clazz -> CliCommandInterface.class.isAssignableFrom(clazz))
                    .filter(clazz -> !clazz.isInterface())
                    .map(Functions.unchecked(clazz -> (CliCommandInterface) clazz.getDeclaredConstructor().newInstance()));

            Stream<CliCommandInterface> annotatedCommandStream = classesInPackage.stream()
                    .filter(clazz -> clazz.isAnnotationPresent(CliCommand.class))
                    .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
                    .filter(method -> method.isAnnotationPresent(CliCommand.class))
                    .filter(method -> method.getParameterCount() == 0)
                    .map(CommandCache::methodToCliCommand);

            Map<String, CliCommandInterface> commands = Stream.concat(cliCommandInterfaceStream, annotatedCommandStream)
                    .map(CommandCache::commandToProxy)
                    .collect(Collectors.toMap(CliCommandInterface::getCommandID, Function.identity()));
            CommandCache.addCommands(commands);

        } catch (IOException e) {
            e.printStackTrace();
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

        Object instance = null;
        try {
            instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        final Object finalInstance = instance;

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
                try {
                    method.invoke(finalInstance);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

}
