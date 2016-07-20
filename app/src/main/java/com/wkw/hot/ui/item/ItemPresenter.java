package com.wkw.hot.ui.item;

import android.app.Activity;

import com.wkw.hot.base.BasePresenter;
import com.wkw.hot.data.DataManager;
import com.wkw.hot.entity.exception.ErrorHanding;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by wukewei on 16/5/30.
 */
public class ItemPresenter extends BasePresenter<ItemContract.View> implements ItemContract.Presenter {

    private static final String key = "new_list";
    protected int pn = 1;

    protected void replacePn() {
        pn = 1;
    }

    private boolean isRefresh() {
        return pn == 1;
    }


    @Inject
    public ItemPresenter(DataManager dataManager, Activity activity ) {
        super(dataManager, activity);

    }

    @Override
    public void getListData(String type) {
        mView.showLoading();
        Subscription subscription = dataManager.getPopular(pn, type)
                .subscribe(populars -> {
                    mView.showContent();
                    if (isRefresh()) {
                        if (populars.size() == 0) mView.showNotdata();
                        mView.addRefreshData(populars);
                    } else {
                        mView.addLoadMoreData(populars);
                    }
                }, throwable -> {
                    if (isRefresh())
                    mView.showError(ErrorHanding.handleError(throwable));
                    handleError(throwable);
                });

        addSubscribe(subscription);

    }

    @Override
    public void getCacheData(String type) {
        mView.showLoading();
        Subscription subscription  = dataManager.getCachePopular(type)
                .subscribe(populars -> {
                    mView.showContent();
                    mView.addLoadMoreData(populars);
                }, throwable -> {
                    mView.showError(ErrorHanding.handleError(throwable));
                    handleError(throwable);
                });

        addSubscribe(subscription);
    }
}
