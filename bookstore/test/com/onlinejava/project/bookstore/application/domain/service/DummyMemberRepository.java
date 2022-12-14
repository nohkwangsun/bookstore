package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.ports.output.MemberRepository;

import java.util.List;
import java.util.Optional;

public class DummyMemberRepository implements MemberRepository {
    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public Optional<Member> findByUserName(String userName) {
        return Optional.empty();
    }

    @Override
    public List<Member> findActiveMembers() {
        return null;
    }

    @Override
    public boolean add(Member member) {

        return false;
    }

    @Override
    public void save() {

    }
}
