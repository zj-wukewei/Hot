package com.wkw.hot.ui.main;

import android.app.Activity;

import com.wkw.hot.base.BasePresenter;
import com.wkw.hot.data.DataManager;

import javax.inject.Inject;

/**
 * Created by wukewei on 16/5/26.
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(DataManager dataManager, Activity activity) {
        super(dataManager, activity);
    }

    @Override
    public void getTabs() {
        //类型暂时先写死
        mView.addTabs(dataManager.getTabs());
    }
}
