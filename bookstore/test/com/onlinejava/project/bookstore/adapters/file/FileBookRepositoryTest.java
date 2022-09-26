package com.onlinejava.project.bookstore.adapters.file;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileBookRepositoryTest {

    private static int CALL_COUNT = 0;

    private FileBookRepository repository = new FileBookRepository() {
        @Override
        public List<Book> getEntityListFromLines(String filePath, Class<Book> clazz) {
            List<Book> list = new ArrayList<>();
            list.add(new Book("refactoring", "martin", "addison", 50, "1/1/2000", "b1", 1));
            list.add(new Book("design pattern", "gof", "addison", 40, "2/2/2022", "b2", 10));
            return list;
        }

        @Override
        public void saveEntityToCSVFile(String fileName, Class<Book> clazz, boolean hasHeader) {
            CALL_COUNT++;
        }
    };

    @BeforeEach
    void beforeEach() {
        repository.initData();
    }

    @Test
    void FileBookRepository() {
        FileBookRepository repository = new FileBookRepository();
        assertNotNull(repository);
    }

    @Test
    void initData() {
        repository.list = null;
        repository.initData();
        assertEquals(2, repository.list.size());
    }

    @Test
    void findAll() {
        List<Book> list = repository.findAll();
        assertEquals(2, list.size());
    }

    @Test
    void save() {
        CALL_COUNT = 0;
        repository.save();
        assertEquals(1, CALL_COUNT);
    }

    @Test
    void findByTitle() {
        Optional<Book> refactoring = repository.findByTitle("refactoring");
        assertTrue(refactoring.isPresent());
        assertEquals("martin", refactoring.get().getWriter());
    }
}