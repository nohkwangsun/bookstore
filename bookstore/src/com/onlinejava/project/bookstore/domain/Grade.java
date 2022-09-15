package com.onlinejava.project.bookstore.domain;

public enum Grade {
    GOLD(0.01) {
        double rate = 0.1;
        @Override
        public int calculatePoint(int price) {
            return price;
        }
    },
    SILVER(0.005) {
        @Override
        public int calculatePoint(int price) {
            return 0;
        }
    },
    BRONZE(0.002) {
        @Override
        public int calculatePoint(int price) {
            return 0;
        }
    },
    GENERAL(0.001) {

        @Override
        public int calculatePoint(int price) {
            return 0;
        }
    };

    private final double rate;

    Grade(double rate) {
        this.rate = rate;
    }

    public abstract int calculatePoint(int price);
}
