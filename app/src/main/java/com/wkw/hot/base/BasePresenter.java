package com.wkw.hot.base;

import android.app.Activity;
import android.util.Log;

import com.wkw.hot.api.HotApi;
import com.wkw.hot.api.HotFactory;
import com.wkw.hot.entity.exception.ServerException;
import com.wkw.hot.utils.Logger;
import com.wkw.hot.utils.NetWorkUtil;
import com.wkw.hot.utils.ToastUtil;

import rx.Subscription;

/**
 * Created by wukewei on 16/5/26.
 */
public abstract class BasePresenter<T extends IView> implements IPresenter {

    protected Activity mActivity;
    protected T mView;
    protected Subscription mSubscription;
    protected static final HotApi mHotApi = HotFactory.getHotApi();

    public BasePresenter(Activity activity, T view) {
        this.mActivity = activity;
        this.mView = view;
    }

    protected void handleError(Throwable throwable) {
        if (!NetWorkUtil.isNetConnected(mActivity)) {
            ToastUtil.showShort(mActivity, "当前网络不可用");
        } else if (throwable instanceof ServerException){
            ToastUtil.showShort(mActivity, throwable.getMessage());
            Logger.e(throwable.getMessage());
        } else {
            ToastUtil.showShort(mActivity,"连接服务器失败,请稍后再试..");
            Logger.e(throwable.getMessage());
        }
    }

    protected void unSubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
