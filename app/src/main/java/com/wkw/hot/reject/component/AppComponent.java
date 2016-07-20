package com.wkw.hot.reject.component;

import com.wkw.hot.data.DataManager;
import com.wkw.hot.reject.ContextLife;
import com.wkw.hot.reject.module.AppModule;
import com.wkw.hot.ui.App;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wukewei on 16/7/19.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    App getContext();

    DataManager getDataManager();

}
