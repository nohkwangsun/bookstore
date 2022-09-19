package com.onlinejava.project.bookstore.infrastructure.file;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.domain.entity.Book;
import com.onlinejava.project.bookstore.ports.output.BookRepository;

import java.util.List;
import java.util.Optional;

public class FileBookRepository extends FileRepository<Book> implements BookRepository {
    @Override
    public List<Book> findAll() {
        return this.list;
    }

    @Override
    public void save() {
        saveEntityToCSVFile("booklist.csv", Book.class, Main.HAS_HEADER);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return this.list.stream()
                .filter(b -> b.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public void initData() {
        this.list = getEntityListFromLines("booklist.csv", Book.class);
    }
}
