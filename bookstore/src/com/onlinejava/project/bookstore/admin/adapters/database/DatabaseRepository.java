package com.onlinejava.project.bookstore.admin.adapters.database;

import com.onlinejava.project.bookstore.common.domain.entity.Entity;

public abstract class DatabaseRepository<T extends Entity> {
    public abstract void initData();
}
