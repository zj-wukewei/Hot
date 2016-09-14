package com.wkw.hot.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import com.wkw.hot.R;
import com.wkw.hot.base.BaseActivity;
import com.wkw.hot.reject.component.AppComponent;
import com.wkw.hot.reject.module.ActivityModule;
import com.wkw.hot.ui.item.ItemFragment;

/**
 * Created by wukewei on 16/9/14.
 */
public class SearchActivity extends BaseActivity {

    public static final String TYPE = "TYPE";
    @Bind(R.id.toolbar) Toolbar toolbar;
    private String type;


    public static void startActivity(Context context, String type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra(TYPE);
        setCommonBackToolBack(toolbar, type);
        if (savedInstanceState == null) {
            ItemFragment fragment = ItemFragment.newInstance(type);
            loadFragment(fragment);
        }
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
            .add(R.id.container, fragment, fragment.getClass().getName())
            .commit();
        fragment.setUserVisibleHint(true);
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {

    }


    @Override protected int getLayout() {
        return R.layout.activity_search;
    }


    @Override protected void initEventAndData() {

    }
}
