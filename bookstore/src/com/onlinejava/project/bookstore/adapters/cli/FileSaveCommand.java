package com.onlinejava.project.bookstore.adapters.cli;

import com.onlinejava.project.bookstore.adapters.file.FileBookRepository;
import com.onlinejava.project.bookstore.adapters.file.FileMemberRepository;
import com.onlinejava.project.bookstore.adapters.file.FilePurchaseRepository;
import com.onlinejava.project.bookstore.core.cli.CliCommandInterface;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

@Deprecated
@SuppressWarnings({"unused"})
public class FileSaveCommand implements CliCommandInterface {
    private FileBookRepository bookRepository;
    private FileMemberRepository memberRepository;
    private FilePurchaseRepository purchaseRepository;

    public FileSaveCommand() {
        bookRepository = BeanFactory.getInstance().get(FileBookRepository.class);
        memberRepository = BeanFactory.getInstance().get(FileMemberRepository.class);
        purchaseRepository = BeanFactory.getInstance().get(FilePurchaseRepository.class);
    }

    public FileSaveCommand(FileBookRepository bookRepository, FileMemberRepository memberRepository, FilePurchaseRepository purchaseRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public void setBookRepository(FileBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void setMemberRepository(FileMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void setPurchaseRepository(FilePurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public String getCommandID() {
        return "s";
    }

    @Override
    public String getTitle() {
        return "Save";
    }

    @Override
    public String getDescription() {
        return "Save changes.";
    }

    @Override
    public void run() {
        bookRepository.save();
        memberRepository.save();
        purchaseRepository.save();
    }
}
