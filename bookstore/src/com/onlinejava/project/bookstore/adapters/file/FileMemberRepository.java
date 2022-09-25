package com.onlinejava.project.bookstore.adapters.file;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.ports.output.MemberRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;

import java.util.List;
import java.util.Optional;

@Bean
public class FileMemberRepository extends FileRepository<Member> implements MemberRepository {
    @Override
    public List<Member> findAll() {
        return this.list;
    }

    @Override
    public Optional<Member> findByUserName(String userName) {
        return this.list.stream()
                .filter(m -> m.getUserName().equals(userName))
                .findFirst();
    }

    @Override
    public List<Member> findActiveMembers() {
        return this.list.stream()
                .filter(Member::isActive)
                .toList();
    }

    @Override
    public void add(Member member) {
        this.list.add(member);
    }

    @Override
    public void save() {
        saveEntityToCSVFile("memberlist.csv", Member.class, Main.HAS_HEADER);
    }

    @Override
    public void initData() {
        this.list = getEntityListFromLines("memberlist.csv", Member.class);
    }
}
