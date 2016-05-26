package com.wkw.hot.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;

/**
 * Created by wukewei on 16/5/26.
 */
public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity {

    protected T mPresenter;
    protected AppCompatActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = getPresenter();
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

    protected abstract T getPresenter();
    protected abstract int getLayout();
    protected abstract void initEventAndData();
}
