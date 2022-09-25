package com.onlinejava.project.bookstore.adapters.file;

import com.onlinejava.project.bookstore.adapters.file.FileBookRepository;
import com.onlinejava.project.bookstore.adapters.file.FileMemberRepository;
import com.onlinejava.project.bookstore.adapters.file.FilePurchaseRepository;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;

public class FileDataInitializer {
    public static void initializeRepositoryData() {
        BeanFactory.getInstance().get(FileBookRepository.class).initData();
        BeanFactory.getInstance().get(FileMemberRepository.class).initData();
        BeanFactory.getInstance().get(FilePurchaseRepository.class).initData();
    }
}