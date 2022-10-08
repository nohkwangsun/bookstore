package com.onlinejava.project.bookstore.admin.application.domain;

import com.onlinejava.project.bookstore.admin.adapters.in.cli.BookCommands;
import com.onlinejava.project.bookstore.admin.application.domain.service.BookService;
import com.onlinejava.project.bookstore.admin.application.ports.input.BookUseCase;
import com.onlinejava.project.bookstore.admin.application.ports.output.BookRepository;
import com.onlinejava.project.bookstore.core.factory.BeanFactory;
import org.junit.jupiter.api.Test;

import static com.onlinejava.project.bookstore.core.util.reflect.ReflectionUtils.getField;
import static org.junit.jupiter.api.Assertions.*;

class BookStoreFactoryTest {

    @Test
    void lookup() {
        BookRepository repository = BeanFactory.getInstance().get(BookRepository.class);
        assertNotNull(repository);
    }

    @Test
    void Inject필드_포함한_Bean객체_인터페이스로_lookup() {
        BookUseCase useCase = BeanFactory.getInstance().get(BookUseCase.class);
        assertNotNull(useCase);
    }

    @Test
    void Bean객체_내_Inject필드_notNull_확인() {
        BookService service = BeanFactory.getInstance().get(BookService.class);
        assertNotNull(service);
        assertNotNull(getField(service, "repository", BookRepository.class));
    }

    @Test
    void CliCommand로_선언된_Bean_객체는_조회안됨() {
        assertThrows(RuntimeException.class, () -> {
            BeanFactory.getInstance().get(BookCommands.class);
        });
    }
}