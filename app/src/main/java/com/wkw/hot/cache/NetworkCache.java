package com.wkw.hot.cache;

import rx.Observable;

/**
 * Created by wukewei on 16/6/19.
 */
public abstract class NetworkCache<T> {
    public abstract Observable<T> get(String key, final Class<T> cls);
}
