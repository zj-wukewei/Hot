package com.wkw.hot.ui.web;

import android.app.Activity;

import com.wkw.hot.base.BasePresenter;
import com.wkw.hot.data.DataManager;

import javax.inject.Inject;

/**
 * Created by wukewei on 16/5/30.
 */
public class WebPresenter extends BasePresenter<WebContract.View> implements WebContract.Presenter {


    @Inject
    public WebPresenter(DataManager dataManager, Activity activity ) {
        super(dataManager, activity);

    }



}
