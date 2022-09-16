package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.domain.model.Member;

import static com.onlinejava.project.bookstore.Main.scanner;
import static com.onlinejava.project.bookstore.domain.service.BookStoreService.bookStoreService;

@CliCommand
@SuppressWarnings({"unused"})
public class MemberCommands {

    @CliCommand(ID = "8", title = "Print member list")
    public void printMemberList() {
        bookStoreService.printMemberList();
    }

    @CliCommand(ID = "9", title = "Add new member")
    public void addMember() {
        System.out.print("Type user name:");
        String userName = scanner.nextLine().trim();
        System.out.print("Type email:");
        String email = scanner.nextLine().trim();
        System.out.print("Type address:");
        String address = scanner.nextLine().trim();
        bookStoreService.addMember(userName, email, address);
    }

    @CliCommand(ID = "10", title = "Withdraw a member")
    public void withdrawMember() {
        System.out.print("Type user name:");
        String userNameToWithdraw = scanner.nextLine().trim();
        bookStoreService.withdrawMember(userNameToWithdraw);
    }

    @CliCommand(ID = "11", title = "Modify a member")
    public void ModifyMember() {
        System.out.print("Type user name:");
        String userNameToModify = scanner.nextLine().trim();
        bookStoreService.getMemberByUserName(userNameToModify).ifPresent(member -> {
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
            bookStoreService.modifyMember(userNameToModify, newMember);
        });
    }
}
