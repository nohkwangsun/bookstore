package com.onlinejava.project.bookstore.admin.application.domain;

import com.onlinejava.project.bookstore.common.domain.exception.UnknownCommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientApplicationTest {

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