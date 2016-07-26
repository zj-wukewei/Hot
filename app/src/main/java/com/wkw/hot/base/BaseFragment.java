package com.wkw.hot.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkw.hot.reject.component.AppComponent;
import com.wkw.hot.reject.module.ActivityModule;
import com.wkw.hot.reject.module.FragmentModule;
import com.wkw.hot.ui.App;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by wukewei on 16/5/30.
 */
public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView{
    @Inject
    protected T mPresenter;
    protected View mView;
    protected Activity mContext;

    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof BaseActivity) {
            mContext = activity;
        }
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        setupActivityComponent(App.getAppComponent(),new FragmentModule(this));
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
        ButterKnife.bind(this, view);
        initEventAndData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }


    /**
     * 依赖注入的入口
     * @param appComponent appComponent
     */
    protected abstract void setupActivityComponent(AppComponent appComponent, FragmentModule fragmentModule);

    protected abstract int getLayoutId();
    protected abstract void initEventAndData();
}
