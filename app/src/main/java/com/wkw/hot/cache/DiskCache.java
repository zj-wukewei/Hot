package com.wkw.hot.cache;

import android.util.Log;

import com.wkw.hot.ui.App;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wukewei on 16/6/19.
 */
public class DiskCache implements ICache{

    private static final String NAME = ".db";
    public static long OTHER_CACHE_TIME = 10 * 60 * 1000;
    public static long WIFI_CACHE_TIME = 30 * 60 * 1000;
    File fileDir;
    public DiskCache() {
        fileDir = CacheLoader.getApplication().getCacheDir();
    }

    @Override
    public <T> Observable<T> get(final String key, final Class<T> cls) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

                T t = (T) getDiskData(key + NAME);

                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if (t == null) {
                    subscriber.onNext(null);
                } else {
                    subscriber.onNext(t);
                }

                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public <T> void put(final String key, final T t) {
        Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

                boolean isSuccess = isSave(key + NAME, t);

                if (!subscriber.isUnsubscribed() && isSuccess) {

                    subscriber.onNext(t);
                    subscriber.onCompleted();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    /**
     * 保存数据
     */
    private <T> boolean isSave(String fileName, T t) {
        File file = new File(fileDir, fileName);

        ObjectOutputStream objectOut = null;
        boolean isSuccess = false;
        try {
            FileOutputStream out = new FileOutputStream(file);
                    objectOut = new ObjectOutputStream(out);
            objectOut.writeObject(t);
            objectOut.flush();
            isSuccess=true;
        } catch (IOException e) {
            Log.e("写入缓存错误",e.getMessage());
        } catch (Exception e) {
            Log.e("写入缓存错误",e.getMessage());
        } finally {
            closeSilently(objectOut);
        }
        return isSuccess;
    }

    /**
     * 获取保存的数据
     */
    private Object getDiskData(String fileName) {
        File file = new File(fileDir, fileName);
        if (!file.exists()) {
            return null;
        }

        if (isCacheDataFailure(file)) {
            return null;
        }

        Object o = null;
        ObjectInputStream read = null;
        try {
            read = new ObjectInputStream(new FileInputStream(file));
            o = read.readObject();
        } catch (StreamCorruptedException e) {
            Log.e("读取错误", e.getMessage());
        } catch (IOException e) {
            Log.e("读取错误", e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("错误", e.getMessage());
        } finally {
            closeSilently(read);
        }
        return o;
    }



    private void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
        }
    }



    /**
     * 判断缓存是否已经失效
     */
    private boolean isCacheDataFailure(File dataFile) {
        if (!dataFile.exists()) {
            return true;
        }
        long existTime = System.currentTimeMillis() - dataFile.lastModified();
        boolean failure = false;

        if (!App.ExtImpl.g().isAvailable()) {
            return failure;
        }
        if (App.ExtImpl.g().isWifi()) {
            failure = existTime > WIFI_CACHE_TIME ? true : false;
        } else {
            failure = existTime > OTHER_CACHE_TIME ? true : false;
        }
        return failure;
    }

    public void clearDisk(String key) {
        File file = new File(fileDir, key + NAME);
        if (file.exists()) file.delete();
    }
}
