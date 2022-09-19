package com.onlinejava.project.bookstore.domain;

import com.onlinejava.project.bookstore.domain.service.BookService;
import com.onlinejava.project.bookstore.domain.service.MemberService;
import com.onlinejava.project.bookstore.domain.service.PurchaseService;
import com.onlinejava.project.bookstore.infrastructure.file.FileBookRepository;
import com.onlinejava.project.bookstore.infrastructure.file.FileMemberRepository;
import com.onlinejava.project.bookstore.infrastructure.file.FilePurchaseRepository;

import java.util.*;

public class BookStoreFactory {
    private static Map<Class, Object> objects = new HashMap<>();;

    public static <T> T lookup(Class<T> clazz) {
        T object = (T) objects.get(clazz);
        if (object == null) {
            throw new RuntimeException("Not found repository: " + clazz.getName());
        }
        return object;
    }

    public static void initializeRepositoryData() {
        lookup(FileBookRepository.class).initData();
        lookup(FileMemberRepository.class).initData();
        lookup(FilePurchaseRepository.class).initData();
    }

    public static void loadObjectsIntoCache() {
        objects.clear();

        putObjectAndInterfaceIntoCache(new FileBookRepository());
        putObjectAndInterfaceIntoCache(new FileMemberRepository());
        putObjectAndInterfaceIntoCache(new FilePurchaseRepository());

        putObjectAndInterfaceIntoCache(new BookService());
        putObjectAndInterfaceIntoCache(new MemberService());
        putObjectAndInterfaceIntoCache(new PurchaseService());

        lookup(BookService.class).setDependency();
        lookup(MemberService.class).setDependency();
        lookup(PurchaseService.class).setDependency();
    }

    private static void putObjectAndInterfaceIntoCache(Object object) {
        objects.put(object.getClass(), object);
        for (Class clazz : object.getClass().getInterfaces()) {
            objects.put(clazz, object);
        }
    }

}