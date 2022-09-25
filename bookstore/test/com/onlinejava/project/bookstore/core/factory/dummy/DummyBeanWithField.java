package com.onlinejava.project.bookstore.core.factory.dummy;

import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;

@Bean
public class DummyBeanWithField {
    @Inject
    public DummyField field;

    public String value;
}
