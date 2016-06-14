package com.wkw.hot.ui.item;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wkw.hot.R;
import com.wkw.hot.base.BaseFragment;
import com.wkw.hot.base.BaseOnScrollListener;
import com.wkw.hot.entity.Popular;
import com.wkw.hot.ui.web.WebActivity;
import com.wkw.hot.utils.Logger;
import com.wkw.hot.utils.ProgressLayout;

import java.util.List;

import butterknife.Bind;

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

    View.OnClickListener tryClick;
    private ItemAdapter mAdapter;
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
        tryClick = v -> {
            mPresenter.replacePn();
            mPresenter.getListData(type);
        };
        mAdapter = new ItemAdapter();
        mAdapter.setOnItemCilckListener((url, title) -> {
            WebActivity.startActivity(mContext, url, title);
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mAdapter);
        type = getArguments().getString(TYPE);
        recyclerView.addOnScrollListener(new BaseOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (mAdapter.canLoadMore()) {
                    mAdapter.setLoading(true);
                    mPresenter.pn++;
                    mPresenter.getListData(type);
                }
            }
        });
        mPresenter.getListData(type);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.replacePn();
            mPresenter.getListData(type);
        });
    }


    @Override
    public void addLoadMoreData(List<Popular> data) {
        mAdapter.addLoadMoreData(data);
    }

    @Override
    public void addRefreshData(List<Popular> data) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
            }, 800);
        }
        mAdapter.addRefreshData(data);
    }

    @Override
    public void showLoading() {
        progressLayout.showLoading();
    }

    @Override
    public void showContent() {
       if (!progressLayout.isContent()) progressLayout.showContent();
    }

    @Override
    public void showNotdata() {
       progressLayout.showNotDta(tryClick);
    }

    @Override
    public void showError(String msg) {
        progressLayout.showError(msg, tryClick);
    }
}
