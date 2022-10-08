package com.onlinejava.project.bookstore.client.application.ports.out;

import com.onlinejava.project.bookstore.common.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository extends Repository {
    Optional<Member> findByUserName(String userName);
}
