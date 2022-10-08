package com.onlinejava.project.bookstore.application.ports.output;

import com.onlinejava.project.bookstore.application.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends Repository {
    List<Member> findAll();

    Optional<Member> findByUserName(String userName);

    List<Member> findActiveMembers();

    boolean add(Member member);
}
