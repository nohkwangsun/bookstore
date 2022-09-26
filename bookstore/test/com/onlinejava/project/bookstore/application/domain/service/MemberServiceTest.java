package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.entity.Grade;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    class MemberRepositoryStub extends DummyMemberRepository {
        public List<Member> list;
        {
            list = new ArrayList<>();
            list.add(new Member("haha", "haha@running.com", "11 sbs road", 100, Grade.SILVER, true));
            list.add(new Member("you jaesuk", "yjs@running.com", "13 sbs road", 1000, Grade.GOLD, true));
            list.add(new Member("gary", "gary@running.com", "3 sbs road", 3, Grade.GENERAL, false));
        }

        @Override
        public Optional<Member> findByUserName(String userName) {
            return list.stream().filter(m -> m.getUserName().equals(userName)).findFirst();
        }

        @Override
        public void add(Member member) {
            list.add(member);
        }

        @Override
        public List<Member> findAll() {
            return list;
        }

        @Override
        public List<Member> findActiveMembers() {
            return list.stream().filter(m -> !m.isActive()).toList();
        }
    }
    class PurchaseRepositoryStub extends DummyPurchaseRepository {
        public List<Purchase> list;
        {
            list = new ArrayList<>();
            list.add(new Purchase("refactoring", "gary", 1, 10000, 10));
            list.add(new Purchase("refactoring", "you jaesuk", 1, 100000, 10));
            list.add(new Purchase("design pattern", "you jaesuk", 1, 100000, 10));
        }

        @Override
        public List<Purchase> findAll() {
            return list;
        }
    }

    private MemberRepositoryStub repository = new MemberRepositoryStub();
    private PurchaseRepositoryStub purchaseRepository = new PurchaseRepositoryStub();
    private MemberService service = new MemberService(repository, purchaseRepository);

    @Test
    void memberService() {
        MemberService service = new MemberService();
        assertNotNull(service);
    }

    @Test
    void modifyMember() {
        Member member = new Member("haha", "haha@running.com", "n/a", 100, Grade.SILVER, true);
        service.modifyMember("haha", member);

        assertEquals("n/a", repository.list.get(0).getAddress());
    }

    @Test
    void getMemberByUserName() {
        Member member = service.getMemberByUserName("gary").get();
        assertEquals("gary", member.getUserName());
    }

    @Test
    void withdrawMember() {
        service.withdrawMember("gary");
        assertFalse(repository.list.get(2).isActive());
    }

    @Test
    void addMember() {
        Member member = new Member("song jihyo", "sjh@running.com", "22 sbs load", 0, Grade.GENERAL, true);

        service.addMember(member.getUserName(), member.getEmail(), member.getAddress());

        assertEquals(member, repository.list.get(3));
    }

    @Test
    void getMemberList() {
        List<Member> list = service.getMemberList();

        assertEquals(repository.list, list);
    }

    @Test
    void getActiveMemberList() {
        List<Member> list = service.getActiveMemberList();

        assertEquals(1, list.size());
        assertEquals("gary", list.get(0).getUserName());
    }

    @Test
    void updateMemberGrades() {
        service.updateMemberGrades();

        Map<Grade, Long> gradeCount = repository.list.stream().collect(Collectors.groupingBy(Member::getGrade, Collectors.counting()));
        assertEquals(gradeCount.get(Grade.SILVER), 1);
        assertEquals(gradeCount.get(Grade.BRONZE), 1);
        assertEquals(gradeCount.get(Grade.GENERAL), 1);
    }
}