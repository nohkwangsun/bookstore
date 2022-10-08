package com.onlinejava.project.bookstore.admin.adapters.out.database;

import com.onlinejava.project.bookstore.common.domain.entity.Book;
import com.onlinejava.project.bookstore.admin.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;

import java.util.List;
import java.util.Optional;

@Bean
public class DatabaseBookRepository extends DatabaseRepository<Book> implements BookRepository {
    @Inject
    protected JdbcTemplate template;

    @Override
    public List<Book> findAll() {
        return template.list("select * from book", Book.class);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        String sql = String.format("select * from book where title = '%s'", title);
        return template.get(sql, Book.class);
    }

    @Override
    public boolean remove(Book book) {
        String sql = String.format("delete from book " +
                        " where title = '%s'" +
                        " and writer = '%s'" +
                        " and publisher = '%s'" +
                        " and release_date = '%s'" +
                        " and price = %d" +
                        " and location = '%s'" +
                        " and stock = %d",
                book.getTitle(),
                book.getWriter(),
                book.getPublisher(),
                book.getReleaseDate(),
                book.getPrice(),
                book.getLocation(),
                book.getStock()
                );
        return template.delete(sql, 1);
    }

    @Override
    public boolean add(Book book) {
        String sql = String.format("insert into book (title, writer, publisher, release_date, price, location, stock)" +
                        " values ('%s', '%s', '%s', '%s', %d, '%s', %d)",
                book.getTitle(),
                book.getWriter(),
                book.getPublisher(),
                book.getReleaseDate(),
                book.getPrice(),
                book.getLocation(),
                book.getStock()
        );
        return template.insert(sql, 1);
    }

    @Override
    public void initData() {
        String bookDDL = "CREATE TABLE IF NOT EXISTS book (" +
                "title varchar(255), " +
                "writer varchar(255), " +
                "publisher varchar(255), " +
                "price numeric, " +
                "release_date varchar(255), " +
                "location varchar(255), " +
                "stock numeric," +
                "primary key (title)" +
                ")";
        template.execute(bookDDL);
    }
}
