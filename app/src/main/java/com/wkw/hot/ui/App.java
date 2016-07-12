package com.wkw.hot.ui;

import android.app.Application;

import com.wkw.hot.data.DataManager;

/**
 * Created by wukewei on 16/5/26.
 */
public class App extends Application {

    private static App appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        DataManager.getInstance().initService();
    }

    public static App getAppContext() {
        return appContext;
    }

}
