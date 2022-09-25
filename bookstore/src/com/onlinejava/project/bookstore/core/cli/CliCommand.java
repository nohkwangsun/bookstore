package com.onlinejava.project.bookstore.core.cli;

import com.onlinejava.project.bookstore.core.factory.Bean;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CliCommand {
    String ID() default "";
    String title() default "";
    String description() default "";
    int order() default Integer.MAX_VALUE;
}
