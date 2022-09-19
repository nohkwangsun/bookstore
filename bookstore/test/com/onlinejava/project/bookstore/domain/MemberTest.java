package com.onlinejava.project.bookstore.domain;

import com.onlinejava.project.bookstore.domain.entity.Grade;
import com.onlinejava.project.bookstore.domain.entity.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    public static final String USER_NAME = "user01";
    public static final String USER_EMAIL = "user01@running.com";
    public static final String USER_ADDRESS = "01 sbs road";
    public static final int USER_POINT = 1;
    public static final String USER_GRADE = "GENERAL";
    public static final boolean USER_ACTIVE = true;

    @Test
    void toCsvString() {
        Member member = newMember();

        String expected = USER_NAME + ", " + USER_EMAIL + ", " + USER_ADDRESS + ", " + USER_POINT + ", " + USER_GRADE + ", " + USER_ACTIVE;
        assertEquals(expected, member.toCsvString());
    }

    private Member newMember() {
        Member member = new Member();
        member.setUserName(USER_NAME);
        member.setEmail(USER_EMAIL);
        member.setAddress(USER_ADDRESS);
        member.setTotalPoint(USER_POINT);
        member.setGrade(Grade.valueOf(USER_GRADE));
        member.setActive(USER_ACTIVE);
        return member;
    }

}