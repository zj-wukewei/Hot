package com.wkw.hot.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wkw.hot.BuildConfig;
import com.wkw.hot.R;
import com.wkw.hot.base.BaseActivity;
import com.wkw.hot.reject.component.AppComponent;
import com.wkw.hot.reject.module.ActivityModule;

import butterknife.Bind;

/**
 * Created by wukewei on 16/7/25.
 */
public class AboutActivity extends BaseActivity {
    @Bind(R.id.tv_version)
    TextView mVersionTv;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.about_collapsing_tool)
    CollapsingToolbarLayout mCollapsingToolbarLayout;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initEventAndData() {
        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> AboutActivity.this.onBackPressed());
        mVersionTv.setText("Version " + BuildConfig.VERSION_NAME);
    }


}
