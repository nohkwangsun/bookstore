package com.onlinejava.project.bookstore.application.domain.service;

import com.onlinejava.project.bookstore.application.domain.entity.Grade;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.domain.entity.Purchase;
import com.onlinejava.project.bookstore.application.ports.input.MemberUseCase;
import com.onlinejava.project.bookstore.application.ports.input.PurchaseUseCase;
import com.onlinejava.project.bookstore.application.ports.output.MemberRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingInt;

@Bean
public class MemberService implements MemberUseCase {

    @Inject
    private MemberRepository repository;

    @Inject
    private PurchaseUseCase purchaseService;

    public MemberService() {
    }

    public MemberService(MemberRepository repository, PurchaseUseCase purchaseService) {
        this.repository = repository;
        this.purchaseService = purchaseService;
    }

    @Override
    public void modifyMember(String userName, Member member) {
        repository.findByUserName(userName)
                .ifPresent(exMember -> {
                    if (!member.getUserName().isBlank()) {
                        exMember.setUserName(member.getUserName());
                    }

                    if (!member.getEmail().isBlank()) {
                        exMember.setEmail(member.getEmail());
                    }

                    if (!member.getAddress().isBlank()) {
                        exMember.setAddress(member.getAddress());
                    }
                });
    }

    @Override
    public Optional<Member> getMemberByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public void withdrawMember(String userName) {
        repository.findByUserName(userName)
                .ifPresent(member -> member.setActive(false));
    }

    @Override
    public void addMember(String userName, String email, String address) {
        Member member = new Member();
        member.setUserName(userName);
        member.setEmail(email);
        member.setAddress(address);
        member.setTotalPoint(0);
        member.setGrade(Grade.BRONZE);
        member.setActive(true);
        repository.add(member);
    }

    @Override
    public List<Member> getMemberList() {
        return repository.findAll();
    }

    @Override
    public List<Member> getActiveMemberList() {
        return repository.findActiveMembers();
    }

    @Override
    public void updateMemberGrades() {
        purchaseService.getPurchaseList().stream()
                .collect(groupingBy(Purchase::getCustomer, summarizingInt(Purchase::getTotalPrice)))
                .forEach((user, stat) -> {
                    Grade newGrade = Grade.getGradeByTotalPrice(stat.getSum());
                    getMemberByUserName(user).ifPresent(member -> member.setGrade(newGrade));
                });

    }
}
