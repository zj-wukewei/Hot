package com.wkw.hot.ui.item;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkw.hot.R;
import com.wkw.hot.base.BaseFragment;
import com.wkw.hot.utils.ProgressLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukewei on 16/5/30.
 */
public class ItemFragment extends BaseFragment<ItemPresenter> implements ItemContract.View {
    public static final String TYPE = "type";

    protected String type;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progress_layout)
    ProgressLayout progressLayout;

    public static ItemFragment newInstance(String type) {
        ItemFragment fragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected ItemPresenter getPresenter() {
        return new ItemPresenter(mContext, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void initEventAndData() {
        type = getArguments().getString(TYPE);
        mPresenter.getListData(type);
        progressLayout.showLoading();

    }


}
