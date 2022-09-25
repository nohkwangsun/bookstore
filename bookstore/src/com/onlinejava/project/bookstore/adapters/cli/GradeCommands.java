package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.core.cli.CliCommand;
import com.onlinejava.project.bookstore.application.ports.input.MemberUseCase;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

import static com.onlinejava.project.bookstore.application.domain.BookStoreApplication.scanner;

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
        System.out.print("Do you want to update the grades of all members? [y(default) / n]:");
        String yn = scanner.nextLine().trim();
        if (yn.equalsIgnoreCase("Y")) {
            memberService.updateMemberGrades();
        } else {
            System.out.println("Canceled");
        }
    }

}
