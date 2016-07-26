package com.wkw.hot.reject.component;

import android.app.Activity;

import com.wkw.hot.data.DataManager;
import com.wkw.hot.reject.PerFragment;
import com.wkw.hot.reject.module.FragmentModule;
import com.wkw.hot.ui.item.ItemFragment;

import dagger.Component;

/**
 * Created by wukewei on 16/7/19.
 */
@PerFragment
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    DataManager getDataManager();

    Activity getActivity();

    void inject(ItemFragment itemFragment);

}
