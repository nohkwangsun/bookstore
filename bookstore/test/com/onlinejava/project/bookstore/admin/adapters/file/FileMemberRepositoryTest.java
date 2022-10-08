package com.onlinejava.project.bookstore.admin.adapters.file;

import com.onlinejava.project.bookstore.common.domain.entity.Grade;
import com.onlinejava.project.bookstore.common.domain.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileMemberRepositoryTest {

    private static int CALL_COUNT = 0;

    private FileMemberRepository repository = new FileMemberRepository() {
        @Override
        public List<Member> getEntityListFromLines(String filePath, Class<Member> clazz) {
            List<Member> list = new ArrayList<>();
            list.add(new Member("haha", "haha@running.com", "11 sbs road", 100, Grade.SILVER, true));
            list.add(new Member("you jaesuk", "yjs@running.com", "13 sbs road", 1000, Grade.GOLD, true));
            list.add(new Member("gary", "gary@running.com", "3 sbs road", 3, Grade.GENERAL, false));
            return list;
        }

        @Override
        public void saveEntityToCSVFile(String fileName, Class<Member> clazz, boolean hasHeader) {
            CALL_COUNT++;
        }
    };

    @BeforeEach
    void beforeEach() {
        repository.initData();
    }

    @Test
    void fileMemberRepository() {
        FileMemberRepository repository = new FileMemberRepository();
        assertNotNull(repository);
    }

    @Test
    void initData() {
        repository.list = null;
        repository.initData();
        assertEquals(3, repository.list.size());
    }

    @Test
    void findAll() {
        List<Member> list = repository.findAll();

        assertEquals(3, list.size());
    }

    @Test
    void findByUserName() {
        Optional<Member> member = repository.findByUserName("haha");

        assertTrue(member.isPresent());
        assertEquals("haha@running.com", member.get().getEmail());
    }

    @Test
    void findActiveMembers() {
        List<Member> list = repository.findActiveMembers();

        assertEquals(2, list.size());
        assertEquals("haha", list.get(0).getUserName());
    }

    @Test
    void add() {
        Member member = new Member("song jihyo", "sjh@running.com", "22 sbs load", 0, Grade.GENERAL, true);
        repository.add(member);

        assertEquals(4, repository.list.size());
        assertEquals("sjh@running.com", repository.list.get(3).getEmail());
    }

    @Test
    void save() {
        CALL_COUNT = 0;

        repository.save();

        assertEquals(1, CALL_COUNT);
    }
}