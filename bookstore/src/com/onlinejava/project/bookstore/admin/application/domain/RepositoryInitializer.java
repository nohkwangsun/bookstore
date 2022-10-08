package com.onlinejava.project.bookstore.admin.application.domain;

import com.onlinejava.project.bookstore.admin.application.ports.output.Repository;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

public class RepositoryInitializer {
    public static void init() {
        BeanFactory.getInstance().list(Repository.class).forEach(Repository::initData);
    }
}