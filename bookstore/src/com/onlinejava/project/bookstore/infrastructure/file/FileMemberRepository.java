package com.onlinejava.project.bookstore.infrastructure.file;

import com.onlinejava.project.bookstore.Main;
import com.onlinejava.project.bookstore.domain.entity.Member;
import com.onlinejava.project.bookstore.ports.output.MemberRepository;

import java.util.List;
import java.util.Optional;

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
