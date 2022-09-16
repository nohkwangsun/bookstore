package com.onlinejava.project.bookstore.domain.model;

public class Purchase extends Model {
    private String title;
    private String customer;
    private int numberOfPurchase;
    private int totalPrice;
    private int point;

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getNumberOfPurchase() {
        return numberOfPurchase;
    }

    public void setNumberOfPurchase(int numberOfPurchase) {
        this.numberOfPurchase = numberOfPurchase;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "title='" + title + '\'' +
                ", customer='" + customer + '\'' +
                ", numberOfPurchase=" + numberOfPurchase +
                ", totalPrice=" + totalPrice +
                ", point=" + point +
                '}';
    }

    public Purchase(String title, String customer, int numberOfPurchase, int totalPrice, int point) {
        this.title = title;
        this.customer = customer;
        this.numberOfPurchase = numberOfPurchase;
        this.totalPrice = totalPrice;
        this.point = point;
    }
}
