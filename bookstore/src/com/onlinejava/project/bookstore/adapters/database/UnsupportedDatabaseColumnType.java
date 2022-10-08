package com.onlinejava.project.bookstore.adapters.database;

import com.onlinejava.project.bookstore.application.domain.exception.ExpectedException;

public class UnsupportedDatabaseColumnType extends ExpectedException {
    public UnsupportedDatabaseColumnType(String message) {
        super(message);
    }
}
