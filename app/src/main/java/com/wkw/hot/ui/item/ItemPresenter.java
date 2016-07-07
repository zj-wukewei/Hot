package com.wkw.hot.ui.item;

import android.app.Activity;

import com.wkw.hot.base.BasePresenter;
import com.wkw.hot.cache.CacheLoader;
import com.wkw.hot.cache.NetworkCache;
import com.wkw.hot.common.Constants;
import com.wkw.hot.entity.ListPopular;
import com.wkw.hot.entity.exception.ErrorHanding;
import com.wkw.hot.utils.RxResultHelper;
import com.wkw.hot.utils.SchedulersCompat;

import rx.Observable;
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

    private NetworkCache<ListPopular> networkCache;


    public ItemPresenter(Activity activity, ItemContract.View view) {
        super(activity, view);

    }

    @Override
    public void getListData(String type) {
        mView.showLoading();
        Subscription subscription = mHotApi.getPopular(ItemPresenter.this.pn, Constants.PAGE_SIZE, type)
                .compose(SchedulersCompat.applyIoSchedulers())
                .compose(RxResultHelper.handleResult())
                .doOnNext(populars -> {
                    if (isRefresh()) {
                        ListPopular popular = new ListPopular(populars);
                        CacheLoader.getInstance(mActivity).upNewData(type, popular);
                    }
                })
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

        networkCache = new NetworkCache<ListPopular>() {
            @Override
            public Observable<ListPopular> get(String key, Class<ListPopular> cls) {
                return mHotApi.getPopular(ItemPresenter.this.pn, Constants.PAGE_SIZE, type)
                        .compose(SchedulersCompat.applyIoSchedulers())
                        .compose(RxResultHelper.handleResult())
                        .flatMap(populars -> {
                            ListPopular popular = new ListPopular(populars);
                            return Observable.just(popular);
                        });
            }
        };

        Subscription subscription  = CacheLoader.getInstance(mActivity)
                .asDataObservable(key + type, ListPopular.class, networkCache)
                .map(listPopular -> listPopular.data)
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
