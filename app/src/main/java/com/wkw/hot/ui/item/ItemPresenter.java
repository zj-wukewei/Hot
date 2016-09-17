package com.wkw.hot.ui.item;

import android.app.Activity;

import com.wkw.common_lib.rx.RxSubscriber;
import com.wkw.hot.base.BasePresenter;
import com.wkw.hot.data.DataManager;
import com.wkw.hot.entity.Popular;

import java.util.List;

import javax.inject.Inject;

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


    @Inject
    public ItemPresenter(DataManager dataManager, Activity activity) {
        super(dataManager, activity);

    }

    @Override
    public void getListData(String type) {
        mView.showLoading();
        Subscription subscription = dataManager.getPopular(pn, type)
                .subscribe(new RxSubscriber<List<Popular>>() {
                    @Override
                    public void _noNext(List<Popular> populars) {
                        mView.showContent();
                        if (isRefresh()) {
                            if (populars.size() == 0) mView.showNotData();
                            mView.addRefreshData(populars);
                        } else {
                            mView.addLoadMoreData(populars);
                        }
                    }

                    @Override
                    public void _onError(String msg) {
                        if (isRefresh())
                            mView.showError(msg);
                    }
                });

        addSubscribe(subscription);

    }

    @Override
    public void getCacheData(String type) {
        mView.showLoading();
        Subscription subscription = dataManager.getCachePopular(type)
                .subscribe(new RxSubscriber<List<Popular>>() {
                    @Override
                    public void _noNext(List<Popular> populars) {
                        mView.showContent();
                        mView.addLoadMoreData(populars);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showError(msg);
                    }
                });

        addSubscribe(subscription);
    }
}
