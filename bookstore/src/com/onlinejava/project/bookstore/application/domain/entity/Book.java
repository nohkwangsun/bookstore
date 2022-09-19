package com.onlinejava.project.bookstore.application.domain.entity;

import java.util.Objects;

public class Book extends Entity {
    private String title;
    private String writer;
    private String publisher;
    private int price;
    private String releaseDate;
    private String location;
    private int stock;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price=" + price +
                ", releaseDate='" + releaseDate + '\'' +
                ", location='" + location + '\'' +
                ", stock=" + stock +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addStock(int stock) {
        this.setStock(this.getStock() + stock);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return price == book.price
                && stock == book.stock
                && Objects.equals(title, book.title)
                && Objects.equals(writer, book.writer)
                && Objects.equals(publisher, book.publisher)
                && Objects.equals(releaseDate, book.releaseDate)
                && Objects.equals(location, book.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, writer, publisher, price, releaseDate, location, stock);
    }
}
