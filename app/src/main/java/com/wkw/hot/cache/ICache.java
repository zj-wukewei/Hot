package com.wkw.hot.cache;

import rx.Observable;

/**
 * Created by wukewei on 16/6/19.
 */
public interface ICache {
    <T> Observable<T> get(String key, Class<T> cls);

    <T> void put(String key, T t);
}
