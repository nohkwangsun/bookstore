package com.onlinejava.project.bookstore;

public class Purchase {
    private String title;
    private String customer;
    private int numberOfPurchase;

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
                '}';
    }

    public Purchase(String title, String customer, int numberOfPurchase) {

        this.title = title;
        this.customer = customer;
        this.numberOfPurchase = numberOfPurchase;
    }

    public String toCsvString() {
        return String.join(", ", title, customer, String.valueOf(numberOfPurchase));
    }
}
