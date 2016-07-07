package com.wkw.hot.cache;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by wukewei on 16/6/19.
 */
public class CacheLoader {
    private static Application application;

    public static Application getApplication() {
        return application;
    }

    private ICache mMemoryCache, mDiskCache;

    private CacheLoader() {

        mMemoryCache = new MemoryCache();
        mDiskCache = new DiskCache();
    }
    private static CacheLoader loader;

    public static CacheLoader getInstance(Context context) {
        application = (Application) context.getApplicationContext();
        if (loader == null) {
            synchronized (CacheLoader.class) {
                if (loader == null) {
                    loader = new CacheLoader();
                }
            }
        }
        return loader;
    }


    public <T> Observable<T> asDataObservable(String key, Class<T> cls, NetworkCache<T> networkCache) {

        Observable observable = Observable.concat(
                memory(key, cls),
                disk(key, cls),
                network(key, cls, networkCache))
                .first(new Func1<T, Boolean>() {
                    @Override
                    public Boolean call(T t) {
                        return t != null;
                    }
                });
        return observable;
    }

    private <T> Observable<T> memory(String key, Class<T> cls) {

        return mMemoryCache.get(key, cls).doOnNext(new Action1<T>() {
            @Override
            public void call(T t) {
                if (null != t) {
                    Log.d("我是来自内存","我是来自内存");
                }
            }
        });
    }

    private <T> Observable<T> disk(final String key, Class<T> cls) {

        return mDiskCache.get(key, cls)
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        if (null != t) {
                            Log.d("我是来自磁盘","我是来自磁盘");
                            mMemoryCache.put(key, t);
                        }
                    }
                });
    }

    public  <T> void upNewData(final String key, T t) {
        Observable.just(t)
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        if (null != t) {
                            Log.d("更新数据","更新数据");
                            mDiskCache.put(key, t);
                            mMemoryCache.put(key, t);
                        }
                    }
                }).subscribe();
    }

    private <T> Observable<T> network(final String key, Class<T> cls
            , NetworkCache<T> networkCache) {

        return networkCache.get(key, cls)
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        if (null != t) {
                            Log.d("我是来自网络","我是来自网络");
                            mDiskCache.put(key, t);
                            mMemoryCache.put(key, t);
                        }
                    }
                });
    }


    public void clearMemory(String key) {
        ((MemoryCache)mMemoryCache).clearMemory(key);
    }



    public void clearMemoryDisk(String key) {
        ((MemoryCache)mMemoryCache).clearMemory(key);
        ((DiskCache)mDiskCache).clearDisk(key);
    }
}
