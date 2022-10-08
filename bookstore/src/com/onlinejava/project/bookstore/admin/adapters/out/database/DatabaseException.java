package com.onlinejava.project.bookstore.admin.adapters.out.database;

import com.onlinejava.project.bookstore.common.domain.exception.ExpectedException;

public class DatabaseException extends ExpectedException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
