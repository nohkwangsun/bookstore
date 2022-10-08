package com.onlinejava.project.bookstore.client.application.ports.in;

import com.onlinejava.project.bookstore.common.domain.entity.Member;

public interface MemberUseCase {
    void modifyMember(String userNameToModify, Member member);

    Member getMemberByUserName(String userName);
}
