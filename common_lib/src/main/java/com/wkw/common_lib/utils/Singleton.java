package com.wkw.common_lib.utils;

/**
 * Created by wukewei on 16/7/24.
 */
public abstract class Singleton<T, P> {
    private volatile  T mInstance;

    protected abstract T create(P p);

    public final T get(P p) {
        if (mInstance == null) {
            synchronized (this) {
                if (mInstance == null) {
                    mInstance = create(p);
                }
            }
        }
        return mInstance;
    }
}
