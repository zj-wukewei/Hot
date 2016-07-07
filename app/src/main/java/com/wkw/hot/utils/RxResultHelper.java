package com.wkw.hot.utils;

import android.util.Log;

import com.wkw.hot.entity.ApiResponse;
import com.wkw.hot.entity.exception.ServerException;
import com.wkw.hot.ui.App;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by wukewei on 16/5/26.
 */
public class RxResultHelper {

    public static <T> Observable.Transformer<ApiResponse<T>, T> handleResult() {
        return new Observable.Transformer<ApiResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<ApiResponse<T>> apiResponseObservable) {
                return apiResponseObservable.flatMap(
                        new Func1<ApiResponse<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(ApiResponse<T> tApiResponse) {
                                if (tApiResponse == null) {
                                    return Observable.empty();
                                } else if (tApiResponse.isSuccess()) {
                                    return createData(tApiResponse.getNewsList());
                                } else {
                                    return Observable.error(new ServerException(tApiResponse.getMsg()));
                                }
                            }
                        }
                );
            }
        };
    }


    public static <T> Observable<T> createData(T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}

