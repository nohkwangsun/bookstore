package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    class BookRepositoryStub extends DummyBookRepository {
        public List<Book> list;
        {
            list = new ArrayList<>();
            list.add(new Book("refactoring", "martin", "addison", 50, "1/1/2000", "b1", 1));
            list.add(new Book("design pattern", "gof", "addison", 40, "2/2/2022", "b2", 10));
        }

        @Override
        public List<Book> findAll() {
            return list;
        }
        @Override
        public Optional<Book> findByTitle(String title) {
            return this.list.stream()
                    .filter(b -> b.getTitle().equals(title))
                    .findFirst();
        }

        @Override
        public boolean add(Book book) {
            return this.list.add(book);
        }

        @Override
        public boolean remove(Book book) {
            return this.list.remove(book);
        }
    }

    BookRepositoryStub bookRepository = new BookRepositoryStub();
    BookService bookService = new BookService(bookRepository);

    @Test
    void bookService() {
        BookService service = new BookService();
        assertNotNull(service);
    }
    @Test
    void getBookList() {
        List<Book> bookList = bookService.getBookList();

        assertEquals(2, bookList.size());
        assertEquals("design pattern", bookList.get(1).getTitle());
        Book[] expected = bookRepository.list.toArray(Book[]::new);
        Book[] actual = bookList.toArray(Book[]::new);
        assertArrayEquals(expected, actual);
    }

    @Test
    void deleteBook() {
        bookService.deleteBook("refactoring");

        assertEquals(1, bookRepository.list.size());
    }

    @Test
    void createBook() {
        Book book = new Book("effective java", "joshua", "addison", 45, "2/2/2002", "f2", 2);

        bookService.createBook(book);

        assertEquals(3, bookRepository.list.size());
        assertTrue(bookRepository.list.indexOf(book) >= 0);
    }

    @Test
    void searchBook() {
        List<Book> list = bookService.searchBook("design pattern");

        assertEquals(1, list.size());
        assertEquals("design pattern", list.get(0).getTitle());
    }

    @Test
    void addStock() {
        bookService.addStock("refactoring", 100);

        assertEquals(101, bookRepository.list.get(0).getStock());
    }
}