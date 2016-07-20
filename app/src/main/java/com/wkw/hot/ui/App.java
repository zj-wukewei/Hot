package com.wkw.hot.ui;

import android.app.Application;

import com.wkw.hot.reject.component.AppComponent;
import com.wkw.hot.reject.component.DaggerAppComponent;
import com.wkw.hot.reject.module.AppModule;

/**
 * Created by wukewei on 16/5/26.
 */
public class App extends Application {

    private static App appContext;
    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();


    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
    public static App getAppContext() {
        return appContext;
    }

}
