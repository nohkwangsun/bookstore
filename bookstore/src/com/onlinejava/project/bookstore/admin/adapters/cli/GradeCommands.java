package com.onlinejava.project.bookstore.admin.adapters.cli;

import com.onlinejava.project.bookstore.admin.application.ports.input.MemberUseCase;
import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

import static com.onlinejava.project.bookstore.core.cli.ConsoleScanner.promptYN;


@CliCommand
@SuppressWarnings({"unused"})
public class GradeCommands {

    private MemberUseCase memberService;

    public GradeCommands() {
        memberService = BeanFactory.getInstance().get(MemberUseCase.class);
    }

    public GradeCommands(MemberUseCase memberService) {
        this.memberService = memberService;
    }

    @CliCommand(ID = "13", title = "Update member's grades")
    public void printUsersPurchaseList() {
        boolean yn = promptYN("Do you want to update the grades of all members?");
        if (yn) {
            memberService.updateMemberGrades();
        } else {
            System.out.println("Canceled");
        }
    }

}
