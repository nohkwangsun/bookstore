package com.onlinejava.project.bookstore.adapters.cli;

import java.lang.reflect.Method;

class StringMethodPair {
    public final String _1;
    public final Method _2;

    public StringMethodPair(String p1, Method p2) {
        this._1 = p1;
        this._2 = p2;
    }

    public static StringMethodPair of(String p1, Method p2) {
        return new StringMethodPair(p1, p2);
    }

    @Override
    public String toString() {
        return "StringMethodPair{" +
                "_1='" + _1 + '\'' +
                ", _2=" + _2.getName() +
                '}';
    }
}