package com.onlinejava.project.bookstore.ports.output;

import com.onlinejava.project.bookstore.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    List<Member> findAll();

    Optional<Member> findByUserName(String userName);

    List<Member> findActiveMembers();

    void add(Member member);

    void save();
}
