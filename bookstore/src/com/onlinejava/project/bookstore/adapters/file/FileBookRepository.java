package com.onlinejava.project.bookstore.adapters.file;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;

import java.util.List;
import java.util.Optional;

@Bean
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
