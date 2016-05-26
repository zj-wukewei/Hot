package com.wkw.hot.ui;

import android.app.Application;

/**
 * Created by wukewei on 16/5/26.
 */
public class App extends Application {

    private static App appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static App getAppContext() {
        return appContext;
    }

}
