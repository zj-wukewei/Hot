package com.wkw.hot.ui.main;

import android.app.Activity;

import com.wkw.hot.base.BasePresenter;

import java.util.List;

/**
 * Created by wukewei on 16/5/26.
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(Activity activity, MainContract.View view) {
        super(activity, view);
    }

    @Override
    public void getTabs(List<String> tabs) {

    }
}
