package com.wkw.common_lib.rx;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.wkw.common_lib.utils.DialogUtil;

import rx.Subscriber;

/**
 * Created by wukewei on 16/8/17.
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> {

    private ProgressDialog mProgressDialog;

    public ProgressSubscriber(Context context, String message) {
        mProgressDialog = DialogUtil.getWaitDialog(context, message);
        Log.d("ProgressSubscriber","ProgressSubscriber");
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgress();
    }

    @Override
    public void onCompleted() {
        dismissProgress();
    }

    @Override
    public void onNext(T t) {
        _noNext(t);
    }

    @Override
    public void onError(Throwable e) {
        _onError(ErrorHanding.handleError(e));
    }



    private void showProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    private void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }



    public abstract void _noNext(T t);
    public abstract void _onError(String msg);
}
