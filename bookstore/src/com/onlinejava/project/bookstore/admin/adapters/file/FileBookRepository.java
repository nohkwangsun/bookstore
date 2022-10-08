package com.onlinejava.project.bookstore.admin.adapters.file;

import com.onlinejava.project.bookstore.admin.AdminLauncher;
import com.onlinejava.project.bookstore.common.domain.entity.Book;
import com.onlinejava.project.bookstore.admin.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;

import java.util.List;
import java.util.Optional;

@Bean
public class FileBookRepository extends FileRepository<Book> implements BookRepository {
    @Override
    public List<Book> findAll() {
        return this.list == null ? List.of() : this.list;
    }

    @Override
    public void save() {
        saveEntityToCSVFile("booklist.csv", Book.class, AdminLauncher.HAS_HEADER);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return this.list.stream()
                .filter(b -> b.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public boolean remove(Book book) {
        return this.list.remove(book);
    }

    @Override
    public boolean add(Book book) {
        return this.list.add(book);
    }

    @Override
    public void initData() {
        this.list = getEntityListFromLines("booklist.csv", Book.class);
    }
}
