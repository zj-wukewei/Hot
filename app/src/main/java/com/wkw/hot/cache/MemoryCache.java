package com.wkw.hot.cache;

import android.text.TextUtils;
import android.util.LruCache;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wukewei on 16/6/19.
 */
public class MemoryCache implements ICache{

    private LruCache<String, String> mCache;

    public MemoryCache() {
        final int maxMemory = (int) Runtime.getRuntime().maxMemory();
        final int cacheSize = maxMemory / 8;
        mCache = new LruCache<String, String>(cacheSize) {
            @Override
            protected int sizeOf(String key, String value) {
                try {
                    return value.getBytes("UTF-8").length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return value.getBytes().length;
                }
            }
        };
    }

    @Override
    public <T> Observable<T> get(final String key, final Class<T> cls) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

                String result = mCache.get(key);

                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if (TextUtils.isEmpty(result)) {
                    subscriber.onNext(null);
                } else {
                    T t = new Gson().fromJson(result, cls);
                    subscriber.onNext(t);
                }

                subscriber.onCompleted();
            }
        });
    }

    @Override
    public <T> void put(String key, T t) {
        if (null != t) {
            mCache.put(key, new Gson().toJson(t));
        }
    }

    public void clearMemory(String key) {
        mCache.remove(key);
    }
}
