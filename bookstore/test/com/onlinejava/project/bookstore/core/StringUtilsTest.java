package com.onlinejava.project.bookstore.core;

import com.onlinejava.project.bookstore.core.util.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void toCapitalize() {
        String cap = StringUtils.toCapitalize("totalPrice");
        assertEquals("TotalPrice", cap);
    }

}