package com.onlinejava.project.bookstore.client.adapters.out.socket;

import com.onlinejava.project.bookstore.client.application.ports.out.MemberRepository;
import com.onlinejava.project.bookstore.common.domain.entity.Member;
import com.onlinejava.project.bookstore.core.factory.Bean;

import java.util.Optional;

@Bean
public class SocketMemberRepository implements MemberRepository {
    @Override
    public Optional<Member> findByUserName(String userName) {
        return Optional.empty();
    }
}
