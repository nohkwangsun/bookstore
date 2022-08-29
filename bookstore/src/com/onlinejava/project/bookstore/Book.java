package com.onlinejava.project.bookstore;

public class Book {
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

    public Book(String title, String writer, String publisher, int price, String releaseDate, String location) {
        this(title, writer, publisher, price, releaseDate, location, 10);
    }

    public Book(String title, String writer, String publisher, int price, String releaseDate, String location, int stock) {
        this.title = title;
        this.writer = writer;
        this.publisher = publisher;
        this.price = price;
        this.releaseDate = releaseDate;
        this.location = location;
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
}
