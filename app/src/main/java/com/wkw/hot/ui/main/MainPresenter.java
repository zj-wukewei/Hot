package com.wkw.hot.ui.main;

import android.app.Activity;

import com.wkw.hot.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wukewei on 16/5/26.
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(Activity activity, MainContract.View view) {
        super(activity, view);
    }

    @Override
    public void getTabs() {
        //类型暂时先写死
        List<String> tabs = new ArrayList<>();
        tabs.add("科技");
        tabs.add("美女");
        tabs.add("生活");
        tabs.add("娱乐");
        tabs.add("搞笑");
        tabs.add("宅男");
        mView.addTabs(tabs);
    }
}
