package com.wkw.hot.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.wkw.hot.reject.component.AppComponent;
import com.wkw.hot.reject.module.ActivityModule;
import com.wkw.hot.ui.App;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by wukewei on 16/5/26.
 */
public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IView {

    @Inject
    protected T mPresenter;
    protected Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        mContext = this;
        setupActivityComponent(App.getAppComponent(),new ActivityModule(this));
        if (mPresenter != null)
        mPresenter.attachView(this);
        initEventAndData();
    }

    protected void setCommonBackToolBack(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mPresenter != null) mPresenter.detachView();
    }

    /**
     * 依赖注入的入口
     * @param appComponent appComponent
     */
    protected abstract void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule);

    protected abstract int getLayout();
    protected abstract void initEventAndData();
}
