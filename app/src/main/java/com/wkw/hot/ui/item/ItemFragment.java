package com.wkw.hot.ui.item;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.wkw.common_lib.network.Network;
import com.wkw.common_lib.network.NetworkState;
import com.wkw.common_lib.network.NetworkStateListener;
import com.wkw.hot.R;
import com.wkw.hot.base.BaseLazyFragment;
import com.wkw.hot.base.BaseOnScrollListener;
import com.wkw.hot.entity.Popular;
import com.wkw.hot.reject.component.AppComponent;
import com.wkw.hot.reject.component.DaggerFragmentComponent;
import com.wkw.hot.reject.module.FragmentModule;
import com.wkw.hot.ui.web.WebActivity;
import com.wkw.hot.utils.ProgressLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wukewei on 16/5/30.
 */
public class ItemFragment extends BaseLazyFragment<ItemPresenter> implements ItemContract.View {
    public static final String TYPE = "type";

    protected String type;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progress_layout)
    ProgressLayout progressLayout;

    View.OnClickListener tryClick;
    @Bind(R.id.llyt_network)
    LinearLayout llytNetwork;
    private ItemAdapter mAdapter;

    public static ItemFragment newInstance(String type) {
        ItemFragment fragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    private NetworkChangeListener mNetworkChangeListener;

    @Override
    protected void onFirstUserVisible() {
        mPresenter.getCacheData(type);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    class NetworkChangeListener implements NetworkStateListener {

        @Override
        public void onNetworkStateChanged(NetworkState lastState, NetworkState newState) {
            if (!newState.isConnected()) {
                showNetWorkLayout();
            } else {
                hideNetWorkLayout();
            }

        }
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent, FragmentModule fragmentModule) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(fragmentModule)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void initEventAndData() {

        mNetworkChangeListener = new NetworkChangeListener();
        Network.addListener(mNetworkChangeListener);
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
        hideRefreshLayout();
        mAdapter.addRefreshData(data);
    }

    @Override
    public void showLoading() {
        if (!progressLayout.isContent())
            progressLayout.showLoading();
    }

    private void hideRefreshLayout() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
            }, 800);
        }
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
        hideRefreshLayout();
        progressLayout.showError(msg, tryClick);
    }

    @OnClick(value = R.id.img_delete)
    public void onDelete(View view) {
        hideNetWorkLayout();
    }

    private void showNetWorkLayout() {
        if (llytNetwork.getVisibility() == View.GONE) {
            llytNetwork.setVisibility(View.VISIBLE);
        }
    }
    private void hideNetWorkLayout() {
        if (llytNetwork.getVisibility() == View.VISIBLE) {
            llytNetwork.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroy() {
        Network.removeListener(mNetworkChangeListener);
        super.onDestroy();
    }
}
