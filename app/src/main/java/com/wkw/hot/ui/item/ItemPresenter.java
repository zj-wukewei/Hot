package com.wkw.hot.ui.item;

import android.app.Activity;
import android.util.Log;

import com.wkw.hot.base.BasePresenter;
import com.wkw.hot.common.Constants;
import com.wkw.hot.entity.exception.ErrorHanding;
import com.wkw.hot.utils.RxResultHelper;
import com.wkw.hot.utils.SchedulersCompat;

import rx.Subscription;

/**
 * Created by wukewei on 16/5/30.
 */
public class ItemPresenter extends BasePresenter<ItemContract.View> implements ItemContract.Presenter {

    protected int pn = 1;

    protected void replacePn() {
        pn = 1;
    }

    private boolean isRefresh() {
        return pn == 1;
    }

    public ItemPresenter(Activity activity, ItemContract.View view) {
        super(activity, view);
    }

    @Override
    public void getListData(String type) {
            if (isRefresh()) mView.showLoading();
            Subscription subscription = mHotApi.getPopular(this.pn, Constants.PAGE_SIZE, type)
                    .compose(SchedulersCompat.applyIoSchedulers())
                    .compose(RxResultHelper.handleResult())
                    .subscribe(populars -> {
                        mView.showContent();
                        if (isRefresh()) {
                            if (populars.size() == 0) mView.showNotdata();
                            mView.addRefreshData(populars);
                        } else {
                            mView.addLoadMoreData(populars);
                        }
                    }, throwable -> {
                        mView.showError(ErrorHanding.handleError(throwable));
                        handleError(throwable);
                    });
        addSubscrebe(subscription);

    }
}
