package com.wkw.hot.ui.react;

import com.github.markzhai.react.preloader.MrReactActivity;
import com.github.markzhai.react.preloader.ReactInfo;

/**
 * Created by wukewei on 16/8/15.
 */
public class MyReactActivity extends MrReactActivity {
    public static final ReactInfo reactInfo = new ReactInfo("MyHot", null);


    @Override
    protected String getMainComponentName() {
        return reactInfo.getMainComponentName();
    }

    @Override
    public ReactInfo getReactInfo() {
        return reactInfo;
    }
}
