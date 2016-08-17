package com.wkw.common_lib.rx;

import com.wkw.common_lib.rx.error.DefaultErrorBundle;
import com.wkw.common_lib.rx.error.ErrorHanding;

import rx.Subscriber;

/**
 * Created by wukewei on 16/8/17.
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {


    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {
        _noNext(t);
    }

    @Override
    public void onError(Throwable e) {
        _onError(ErrorHanding.handleError(new DefaultErrorBundle((Exception) e)));
    }

    public abstract void _noNext(T t);
    public abstract void _onError(String msg);
}
