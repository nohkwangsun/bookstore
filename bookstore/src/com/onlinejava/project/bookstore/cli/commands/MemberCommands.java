package com.onlinejava.project.bookstore.cli.commands;

import com.onlinejava.project.bookstore.Member;
import com.onlinejava.project.bookstore.core.cli.CliCommand;

import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.bookstore;
import static com.onlinejava.project.bookstore.core.cli.CliCommandInterface.scanner;

@CliCommand
public class MemberCommands {

    @CliCommand(ID = "8", title = "Print member list")
    public void printMemberList() {
        bookstore.printMemberList();
    }

    @CliCommand(ID = "9", title = "Add new member")
    public void addMember() {
        System.out.printf("Type user name:");
        String userName = scanner.nextLine().trim();
        System.out.printf("Type email:");
        String email = scanner.nextLine().trim();
        System.out.printf("Type address:");
        String address = scanner.nextLine().trim();
        bookstore.addMember(userName, email, address);
    }

    @CliCommand(ID = "10", title = "Withdraw a member")
    public void withdrawMember() {
        System.out.printf("Type user name:");
        String userNameToWithdraw = scanner.nextLine().trim();
        bookstore.withdrawMember(userNameToWithdraw);
    }

    @CliCommand(ID = "11", title = "Modify a member")
    public void ModifyMember() {
        System.out.printf("Type user name:");
        String userNameToModify = scanner.nextLine().trim();
        bookstore.getMemberByUserName(userNameToModify).ifPresent(member -> {
            System.out.printf("Type new user name [default:%s] :", member.getUserName());
            String newUserName = scanner.nextLine().trim();
            System.out.printf("Type email [default:%s] :", member.getEmail());
            String newEmail = scanner.nextLine().trim();
            System.out.printf("Type address [default:%s] :", member.getAddress());
            String newAddress = scanner.nextLine().trim();
            bookstore.modifyMember(userNameToModify, new Member(newUserName, newEmail, newAddress));
        });
    }
}