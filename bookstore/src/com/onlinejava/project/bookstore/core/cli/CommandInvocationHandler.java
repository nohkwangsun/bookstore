package com.onlinejava.project.bookstore.core.cli;

import com.onlinejava.project.bookstore.core.util.reflect.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;

public class CommandInvocationHandler implements InvocationHandler {
    private CliCommandInterface cliCommand;

    public CommandInvocationHandler(CliCommandInterface cliCommand) {
        this.cliCommand = cliCommand;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        boolean target = method.getName().equals("run") && method.getParameterCount() == 0;
        if (!target) {
            return ReflectionUtils.invoke(method, cliCommand, args);
        }

        Object result = ReflectionUtils.invoke(method, cliCommand, args);
//        System.out.println("Press enter for the menu...");
//        scanner.nextLine();

        return result;
    }
}
