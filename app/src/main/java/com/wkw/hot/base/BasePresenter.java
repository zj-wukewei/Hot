package com.wkw.hot.base;

import android.app.Activity;
import android.util.Log;

import com.wkw.hot.api.HotApi;
import com.wkw.hot.api.HotFactory;
import com.wkw.hot.entity.exception.ErrorHanding;
import com.wkw.hot.entity.exception.ServerException;
import com.wkw.hot.utils.Logger;
import com.wkw.hot.utils.NetWorkUtil;
import com.wkw.hot.utils.ToastUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wukewei on 16/5/26.
 */
public abstract class BasePresenter<T extends IView> implements IPresenter {

    protected Activity mActivity;
    protected T mView;
    protected CompositeSubscription mCompositeSubscription;
    protected static final HotApi mHotApi = HotFactory.getHotApi();

    public BasePresenter(Activity activity, T view) {
        this.mActivity = activity;
        this.mView = view;
    }

    protected void handleError(Throwable throwable) {
        ToastUtil.showShort(mActivity, ErrorHanding.handleError(throwable));
    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
