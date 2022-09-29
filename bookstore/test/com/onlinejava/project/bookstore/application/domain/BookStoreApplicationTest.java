package com.onlinejava.project.bookstore.application.domain;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.domain.exception.UnknownCommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookStoreApplicationTest {

    BookStoreApplication app = new BookStoreApplication();

    @Test
    void bookStoreApplication() {
        BookStoreApplication app = new BookStoreApplication();
        assertNotNull(app);
    }

    @Test
    void runCommand() {
        UnknownCommandException e = assertThrows(UnknownCommandException.class, () -> {
            app.runCommand("X");
        });

        assertEquals("[X] is not a valid command", e.getMessage());
        assertEquals("X", e.getCommandNumber());


    }
}