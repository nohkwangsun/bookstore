package com.onlinejava.project.bookstore.ports.input;

import com.onlinejava.project.bookstore.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberUseCase {
    void modifyMember(String userNameToModify, Member member);

    Optional<Member> getMemberByUserName(String userName);

    void withdrawMember(String userName);

    void addMember(String userName, String email, String address);

    List<Member> getMemberList();

    List<Member> getActiveMemberList();

    void updateMemberGrades();
}
