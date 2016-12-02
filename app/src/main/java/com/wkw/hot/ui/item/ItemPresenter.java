package com.wkw.hot.ui.item;

import com.wkw.common_lib.rx.RxSubscriber;
import com.wkw.hot.base.BasePresenter;
import com.wkw.hot.data.DataManager;
import com.wkw.hot.entity.PopularEntity;
import com.wkw.hot.mapper.PopularModelDataMapper;
import com.wkw.hot.model.PopularModel;
import java.util.List;
import javax.inject.Inject;
import rx.Subscription;

/**
 * Created by wukewei on 16/5/30.
 */
public class ItemPresenter extends BasePresenter<ItemContract.View> implements ItemContract.Presenter {

    protected int pn = 1;
    private final DataManager mDataManager;
    private final PopularModelDataMapper mPopularModelDataMapper;

    protected void replacePn() {
        pn = 1;
    }

    private boolean isRefresh() {
        return pn == 1;
    }

    @Inject
    public ItemPresenter(DataManager dataManager, PopularModelDataMapper popularModelDataMapper) {
        mDataManager = dataManager;
        mPopularModelDataMapper = popularModelDataMapper;
    }

    @Override
    public void getListData(String type) {
        mView.showLoading();
        Subscription subscription = mDataManager.getPopular(pn, type)
            .map(popularEntityList -> mPopularModelDataMapper.transform(popularEntityList))
            .subscribe(new RxSubscriber<List<PopularModel>>() {
                    @Override
                    public void _noNext(List<PopularModel> populars) {
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
        Subscription subscription = mDataManager.getCachePopular(type)
            .map(popularEntityList -> mPopularModelDataMapper.transform(popularEntityList))
            .subscribe(new RxSubscriber<List<PopularModel>>() {
                    @Override
                    public void _noNext(List<PopularModel> populars) {
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
