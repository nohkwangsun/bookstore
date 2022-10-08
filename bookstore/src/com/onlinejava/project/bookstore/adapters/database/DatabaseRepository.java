package com.onlinejava.project.bookstore.adapters.database;

import com.onlinejava.project.bookstore.application.domain.entity.Entity;

public abstract class DatabaseRepository<T extends Entity> {
    public abstract void initData();
}
