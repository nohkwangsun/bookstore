package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.domain.BookStoreFactory;
import com.onlinejava.project.bookstore.application.ports.input.MemberUseCase;

import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;

@CliCommand
@SuppressWarnings({"unused"})
public class MemberCommands {

    private MemberUseCase service;

    public MemberCommands() {
        service = BookStoreFactory.lookup(MemberUseCase.class);
    }

    public MemberCommands(MemberUseCase service) {
        this.service = service;
    }

    @CliCommand(ID = "8", title = "Print member list")
    public void printMemberList() {
        service.getActiveMemberList().forEach(System.out::println);
    }

    @CliCommand(ID = "9", title = "Add new member")
    public void addMember() {
        System.out.print("Type user name:");
        String userName = scanner.nextLine().trim();
        System.out.print("Type email:");
        String email = scanner.nextLine().trim();
        System.out.print("Type address:");
        String address = scanner.nextLine().trim();
        service.addMember(userName, email, address);
    }

    @CliCommand(ID = "10", title = "Withdraw a member")
    public void withdrawMember() {
        System.out.print("Type user name:");
        String userNameToWithdraw = scanner.nextLine().trim();
        service.withdrawMember(userNameToWithdraw);
    }

    @CliCommand(ID = "11", title = "Modify a member")
    public void ModifyMember() {
        System.out.print("Type user name:");
        String userNameToModify = scanner.nextLine().trim();
        service.getMemberByUserName(userNameToModify).ifPresent(member -> {
            System.out.printf("Type new user name [default:%s] :", member.getUserName());
            String newUserName = scanner.nextLine().trim();
            System.out.printf("Type email [default:%s] :", member.getEmail());
            String newEmail = scanner.nextLine().trim();
            System.out.printf("Type address [default:%s] :", member.getAddress());
            String newAddress = scanner.nextLine().trim();
            Member newMember = new Member();
            newMember.setUserName(newUserName);
            newMember.setEmail(newEmail);
            newMember.setAddress(newAddress);
            newMember.setTotalPoint(member.getTotalPoint());
            newMember.setActive(member.isActive());
            service.modifyMember(userNameToModify, newMember);
        });
    }
}
