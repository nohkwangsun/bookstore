package com.onlinejava.project.bookstore.admin.application.domain.service;

import com.onlinejava.project.bookstore.common.domain.entity.Grade;
import com.onlinejava.project.bookstore.common.domain.entity.Member;
import com.onlinejava.project.bookstore.common.domain.entity.Purchase;
import com.onlinejava.project.bookstore.common.domain.exception.DuplicateItemException;
import com.onlinejava.project.bookstore.common.domain.exception.NoSuchItemException;
import com.onlinejava.project.bookstore.admin.application.ports.input.MemberUseCase;
import com.onlinejava.project.bookstore.admin.application.ports.output.MemberRepository;
import com.onlinejava.project.bookstore.admin.application.ports.output.PurchaseRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;
import com.onlinejava.project.bookstore.core.util.validator.Validators;

import java.util.List;

import static com.onlinejava.project.bookstore.common.domain.exception.TooManyItemsException.Term.MemberName;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingInt;

@Bean
public class MemberService implements MemberUseCase {

    @Inject
    private MemberRepository repository;

    @Inject
    private PurchaseRepository purchaseRepository;

    public MemberService() {
    }

    public MemberService(MemberRepository repository, PurchaseRepository purchaseRepository) {
        this.repository = repository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void modifyMember(String userName, Member member) {
        Member exMember = repository.findByUserName(userName)
                .orElseThrow(() -> new NoSuchItemException(MemberName, userName));

        exMember.setUserName(member.getUserName());
        exMember.setEmail(member.getEmail());
        exMember.setAddress(member.getAddress());
    }


    @Override
    public Member getMemberByUserName(String userName) {
        return repository.findByUserName(userName)
                .orElseThrow(() -> new NoSuchItemException(MemberName, userName));
    }

    @Override
    public void withdrawMember(String userName) {
        Member member = repository.findByUserName(userName)
                .orElseThrow(() -> new NoSuchItemException(MemberName, userName));
        member.setActive(false);
    }

    @Override
    public void addMember(String userName, String email, String address) {
        repository.findByUserName(userName)
                .ifPresent(DuplicateItemException.consumerOf(MemberName, userName));
        Member member = new Member(userName, email, address, 0, Grade.GENERAL, true);
        repository.add(member);
    }

    @Override
    public List<Member> getMemberList() {
        List<Member> list = repository.findAll();
        Validators.throwIfEmpty(list, Member.class);
        return list;
    }

    @Override
    public List<Member> getActiveMemberList() {
        List<Member> list = repository.findActiveMembers();
        Validators.throwIfEmpty(list, Member.class);
        return list;
    }

    @Override
    public void updateMemberGrades() {
        getMemberList().forEach(m -> m.setGrade(Grade.GENERAL));
        purchaseRepository.findAll().stream()
                .collect(groupingBy(Purchase::getCustomer, summarizingInt(Purchase::getTotalPrice)))
                .forEach((user, stat) -> {
                    Grade newGrade = Grade.getGradeByTotalPrice(stat.getSum());
                    repository.findByUserName(user).ifPresent(m -> m.setGrade(newGrade));
                });
    }
}
