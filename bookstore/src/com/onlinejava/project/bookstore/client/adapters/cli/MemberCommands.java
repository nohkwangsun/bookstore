package com.onlinejava.project.bookstore.client.adapters.cli;

import com.onlinejava.project.bookstore.client.application.domain.ClientApplication;
import com.onlinejava.project.bookstore.client.application.ports.input.MemberUseCase;
import com.onlinejava.project.bookstore.common.domain.entity.Member;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.core.cli.ConsolePrinter;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

import java.util.List;

import static com.onlinejava.project.bookstore.core.cli.ConsoleScanner.prompt;
import static com.onlinejava.project.bookstore.core.cli.ConsoleScanner.promptDefaultValue;


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

    @CliCommand(ID = "8", title = "Print member")
    public void printMemberList() {
        Member member = service.getMemberByUserName(ClientApplication.getUserName());
        printer.printList(List.of(member));
    }

    @CliCommand(ID = "11", title = "Modify a member")
    public void ModifyMember() {
        String userName = prompt("user name");
        Member member = service.getMemberByUserName(userName);

        String newEmail = promptDefaultValue("email", member.getEmail());
        String newAddress = promptDefaultValue("address", member.getAddress());

        Member newMember = new Member();
        newMember.setUserName(member.getUserName());
        newMember.setEmail(newEmail);
        newMember.setAddress(newAddress);
        newMember.setTotalPoint(member.getTotalPoint());
        newMember.setActive(member.isActive());
        service.modifyMember(userName, newMember);
    }
}
