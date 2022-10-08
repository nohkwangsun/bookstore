package com.onlinejava.project.bookstore.admin.adapters.out.database;

import com.onlinejava.project.bookstore.common.domain.exception.ExpectedException;

public class UnsupportedDatabaseColumnType extends ExpectedException {
    public UnsupportedDatabaseColumnType(String message) {
        super(message);
    }
}
