package com.onlinejava.project.bookstore.client.application.domain.service;

import com.onlinejava.project.bookstore.client.application.domain.exception.NoSuchItemException;
import com.onlinejava.project.bookstore.client.application.ports.input.MemberUseCase;
import com.onlinejava.project.bookstore.client.application.ports.output.MemberRepository;
import com.onlinejava.project.bookstore.client.application.ports.output.PurchaseRepository;
import com.onlinejava.project.bookstore.common.domain.entity.Member;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;

import static com.onlinejava.project.bookstore.common.domain.exception.TooManyItemsException.Term.MemberName;

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

}
