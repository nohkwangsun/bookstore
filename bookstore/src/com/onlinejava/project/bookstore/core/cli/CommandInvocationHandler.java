package com.onlinejava.project.bookstore.core.cli;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.onlinejava.project.bookstore.Main.scanner;

public class CommandInvocationHandler implements InvocationHandler {
    private CliCommandInterface cliCommand;

    public CommandInvocationHandler(CliCommandInterface cliCommand) {
        this.cliCommand = cliCommand;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean target = method.getName().equals("run") && method.getParameterCount() == 0;
        if (!target) {
            return method.invoke(cliCommand, args);
        }

        Object result = method.invoke(cliCommand);
        System.out.println("Press enter for the menu...");
        scanner.nextLine();

        return result;
    }
}
