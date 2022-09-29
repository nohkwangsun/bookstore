package com.onlinejava.project.bookstore.application.ports.input;

import com.onlinejava.project.bookstore.application.domain.entity.Member;

import java.util.List;

public interface MemberUseCase {
    void modifyMember(String userNameToModify, Member member);

    Member getMemberByUserName(String userName);

    void withdrawMember(String userName);

    void addMember(String userName, String email, String address);

    List<Member> getMemberList();

    List<Member> getActiveMemberList();

    void updateMemberGrades();
}
