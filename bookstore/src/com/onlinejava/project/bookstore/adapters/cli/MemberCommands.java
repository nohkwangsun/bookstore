package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.application.domain.entity.Book;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.application.domain.entity.Member;
import com.onlinejava.project.bookstore.application.ports.input.MemberUseCase;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

import java.util.List;

import static com.onlinejava.project.bookstore.adapters.cli.ConsoleUtils.prompt;
import static com.onlinejava.project.bookstore.adapters.cli.ConsoleUtils.promptDefaultValue;
import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;

@CliCommand
@SuppressWarnings({"unused"})
public class MemberCommands {

    private MemberUseCase service;
    private ConsolePrinter<Member> printer = new ConsolePrinter<>(Member.class);

    public MemberCommands() {
        service = BeanFactory.getInstance().get(MemberUseCase.class);
    }

    public MemberCommands(MemberUseCase service) {
        this.service = service;
    }

    @CliCommand(ID = "8", title = "Print member list")
    public void printMemberList() {
        printer.printList(service.getActiveMemberList());
    }

    @CliCommand(ID = "9", title = "Add new member")
    public void addMember() {
        String userName = prompt("user name");
        String email = prompt("email");
        String address = prompt("address");
        service.addMember(userName, email, address);
    }

    @CliCommand(ID = "10", title = "Withdraw a member")
    public void withdrawMember() {
        String userName = prompt("user name");
        service.withdrawMember(userName);
    }

    @CliCommand(ID = "11", title = "Modify a member")
    public void ModifyMember() {
        String userName = prompt("user name");
        Member member = service.getMemberByUserName(userName);

        String newUserName = promptDefaultValue("user name", member.getUserName());
        String newEmail = promptDefaultValue("email", member.getEmail());
        String newAddress = promptDefaultValue("address", member.getAddress());

        Member newMember = new Member();
        newMember.setUserName(newUserName);
        newMember.setEmail(newEmail);
        newMember.setAddress(newAddress);
        newMember.setTotalPoint(member.getTotalPoint());
        newMember.setActive(member.isActive());
        service.modifyMember(userName, newMember);
    }
}
