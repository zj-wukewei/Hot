package com.wkw.hot.reject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * Created by wukewei on 16/7/19.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ContextLife {
    String value() default "Application";
}

