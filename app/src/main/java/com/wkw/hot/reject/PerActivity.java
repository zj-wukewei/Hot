package com.wkw.hot.reject;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.inject.Scope;

/**
 * Created by wukewei on 16/7/19.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
