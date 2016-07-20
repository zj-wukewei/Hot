package com.wkw.hot.reject.component;

import android.app.Activity;

import com.wkw.hot.data.DataManager;
import com.wkw.hot.reject.PerActivity;
import com.wkw.hot.reject.module.ActivityModule;
import com.wkw.hot.ui.main.MainActivity;
import com.wkw.hot.ui.web.WebActivity;

import dagger.Component;

/**
 * Created by wukewei on 16/7/19.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    DataManager getDataManager();

    Activity getActivity();

    void inject(MainActivity mainActivity);
    void inject(WebActivity webActivity);

}
