package com.wkw.hot.base;

import android.app.Activity;

import com.wkw.hot.api.HotApi;
import com.wkw.hot.api.HotFactory;

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

    protected void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    @Override
    public void detachView() {
        this.mView = null;
        unsubscribe();
    }
}
