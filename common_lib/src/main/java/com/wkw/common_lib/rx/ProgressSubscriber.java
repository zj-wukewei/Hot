package com.wkw.common_lib.rx;

import android.app.ProgressDialog;
import android.content.Context;

import com.wkw.common_lib.rx.error.DefaultErrorBundle;
import com.wkw.common_lib.rx.error.ErrorHanding;
import com.wkw.common_lib.utils.DialogUtil;

import rx.Subscriber;

/**
 * Created by wukewei on 16/8/17.
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> {

    private ProgressDialog mProgressDialog;

    public ProgressSubscriber(Context context, String message) {
        mProgressDialog = DialogUtil.getWaitDialog(context, message);
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
        dismissProgress();
        _onError(ErrorHanding.handleError(new DefaultErrorBundle((Exception) e)));
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
