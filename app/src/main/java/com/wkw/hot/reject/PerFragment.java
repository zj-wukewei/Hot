package com.wkw.hot.reject;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by wukewei on 16/7/19.
 */
@Scope
@Retention(RUNTIME)
public @interface PerFragment {
}
