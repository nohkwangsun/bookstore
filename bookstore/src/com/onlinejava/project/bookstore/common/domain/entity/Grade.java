package com.onlinejava.project.bookstore.common.domain.entity;

public enum Grade {
    GOLD(0.01f, 300000),
    SILVER(0.005f, 100000),
    BRONZE(0.002f, 10000),
    GENERAL(0.001f, 0);

    protected float rate;
    protected int minTotalPrice;

    Grade(float rate, int minTotalPrice) {
        this.rate = rate;
        this.minTotalPrice = minTotalPrice;
    }

    public static Grade getGradeByTotalPrice(long totalPrice) {
        if (totalPrice >= GOLD.minTotalPrice) return GOLD;
        if (totalPrice >= SILVER.minTotalPrice) return SILVER;
        if (totalPrice >= BRONZE.minTotalPrice) return BRONZE;
        return GENERAL;
    }

    public int calculatePoint(int bookPrice) {
        return Math.round(bookPrice * rate);
    };
}
