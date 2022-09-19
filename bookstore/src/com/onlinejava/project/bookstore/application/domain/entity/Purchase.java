package com.onlinejava.project.bookstore.application.domain.entity;

import java.util.Objects;

public class Purchase extends Entity {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return numberOfPurchase == purchase.numberOfPurchase
                && totalPrice == purchase.totalPrice
                && point == purchase.point
                && Objects.equals(title, purchase.title)
                && Objects.equals(customer, purchase.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, customer, numberOfPurchase, totalPrice, point);
    }
}
