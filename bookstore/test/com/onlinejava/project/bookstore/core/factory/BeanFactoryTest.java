package com.onlinejava.project.bookstore.core.factory;

import com.onlinejava.project.bookstore.core.factory.dummy.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BeanFactoryTest {

    private BeanFactory factory = BeanFactory.getInstance();

    @Test
    void 빈팩토리객체_생성() {
        BeanFactory factory = BeanFactory.getInstance();
        assertNotNull(factory);
    }

    @Test
    void 빈팩토리객체_싱글톤으로_생성() {
        BeanFactory factory = BeanFactory.getInstance();
        BeanFactory factory2 = BeanFactory.getInstance();
        assertEquals(factory, factory2);
    }

    @Test
    void 타입으로_객체_반환_실패하기() {
        assertThrows(RuntimeException.class, () -> {
            DummyClass object = factory.get(DummyClass.class);
        });
    }

    @Test
    void 타입으로_Bean객체_반환하기() {
        DummyBean object = factory.get(DummyBean.class);
        assertNotNull(object);
    }

    @Test
    void 인터페이스로_Bean_객체_반환하기() {
        DummyInterface object = factory.get(DummyInterface.class);
        assertNotNull(object);
    }

    @Test
    void Inject된_Bean객체_반환하기() {
        DummyBeanWithField object = factory.get(DummyBeanWithField.class);
        assertNotNull(object.field);
    }

    @Test
    void Inject되지_않은_객체는_null_반환하기() {
        BeanFactory factory = BeanFactory.getInstance();
        DummyBeanWithField object = factory.get(DummyBeanWithField.class);
        assertNull(object.value);
    }

    @Test
    void 인터페이스에_구현체_Inject_하여_반환하기() {
        DummyBeanWithIField object = factory.get(DummyBeanWithIField.class);
        assertNotNull(object.field);
    }

    @Test
    void 주입된_객체가_동일한_객체들인지_확인() {
        DummyBeanWithIField object = factory.get(DummyBeanWithIField.class);
        DummyBeanWithIField object2 = factory.get(DummyBeanWithIField.class);
        assertEquals(object, object2);
        assertEquals(object.field, object2.field);
    }

    @Test
    void 재귀적으로_객체가_주입되는지_확인() {
        DummyBeanWithNestedBeans bean = factory.get(DummyBeanWithNestedBeans.class);
        assertNotNull(bean.outerBean);
        assertNotNull(bean.outerBean.field);
    }
}
