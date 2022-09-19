package com.onlinejava.project.bookstore.domain;

import com.onlinejava.project.bookstore.domain.entity.Grade;
import com.onlinejava.project.bookstore.domain.entity.Member;
import com.onlinejava.project.bookstore.infrastructure.file.FileMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberRepositoryTest {

    private FileMemberRepository memberRepository = new FileMemberRepository();
    private List<String> csv;
    private List<String> csvWithHeader;
    private Member kim;
    private Member yoo;

    @BeforeEach
    void beforeAll() {

        kim = new Member();
        kim.setUserName("김종국");
        kim.setActive(true);
        kim.setGrade(Grade.GENERAL);
        kim.setAddress("11 sbs road");
        kim.setEmail("kjk@running.com");
        kim.setTotalPoint(5000);

        yoo = new Member();
        yoo.setUserName("유재석");
        yoo.setActive(true);
        yoo.setGrade(Grade.GOLD);
        yoo.setAddress("15 sbs road");
        yoo.setEmail("yjs@running.com");
        yoo.setTotalPoint(10000);

        csv = Arrays.stream("""
        김종국, kjk@running.com, 11 sbs road, 5000, GENERAL, true
        유재석, yjs@running.com, 15 sbs road, 10000, GOLD, true
        """.split("\\n")).toList();

        csvWithHeader = Arrays.stream("""
        userName, email, address, totalPoint, grade, active
        김종국, kjk@running.com, 11 sbs road, 5000, GENERAL, true
        유재석, yjs@running.com, 15 sbs road, 10000, GOLD, true
        """.split("\\n")).toList();

    }

    @Test
    void createEntityByCSVTestWithFalseParam() {
        List<Member> members = memberRepository.getEntityListFromLines(csv, Member.class, false);

        assertEquals(2, members.size());
        assertEquals(kim, members.get(0));
        assertEquals(yoo, members.get(1));
    }

    @Test
    void createEntityByCSVTestWithTrueParam() {
        List<Member> members = memberRepository.getEntityListFromLines(csvWithHeader, Member.class, true);

        assertEquals(2, members.size());
        assertEquals(kim, members.get(0));
        assertEquals(yoo, members.get(1));
    }
}
