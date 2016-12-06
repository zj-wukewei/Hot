package com.wkw.hot.base.page;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import com.wkw.common_lib.widget.ProgressLayout;
import com.wkw.common_lib.widget.loadmore.OnLoadMoreListener;
import com.wkw.common_lib.widget.loadmore.RecyclerViewWithFooter;
import com.wkw.hot.R;
import com.wkw.hot.base.BaseFragment;
import com.wkw.hot.base.ILoadingView;
import com.wkw.hot.base.IPresenter;
import com.wkw.hot.common.Constants;
import java.util.List;

/**
 * Created by wukewei on 16/12/6.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public abstract class BasePageFragment<T extends IPresenter> extends BaseFragment<T> implements
    IDataVIew {

    @Bind(R.id.recycler_view) RecyclerViewWithFooter mRecyclerViewWithFooter;
    @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.progress_layout) ProgressLayout mProgressLayout;


    public int mCurrentPage = 1;

    public boolean mIsFetching = false;

    public boolean isInit = true;


    @Override protected void initEventAndData() {
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRecyclerViewWithFooter.setOnLoadMoreListener(mLoadMoreListener);
        mRecyclerViewWithFooter.setAdapter(getAdapter());
        mRecyclerViewWithFooter.setLayoutManager(getLayoutManager());
    }


    @Override protected int getLayoutId() {
        return R.layout.fragment_list;
    }


    public OnLoadMoreListener mLoadMoreListener = () -> {
        mCurrentPage++;
        fetchData(mCurrentPage);
    };

    public SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = () -> {
        if (!mIsFetching && mSwipeRefreshLayout != null &&
            mSwipeRefreshLayout.isRefreshing()) {
            mCurrentPage = 1;
            mRecyclerViewWithFooter.setPullToLoad();
        }
    };


    @Override public void showContent() {
        if (mProgressLayout != null) {
            mProgressLayout.showContent();
        }
    }


    @Override public void showLoading() {
        mIsFetching = true;
        if (mCurrentPage == 1 && isInit) {
            if (mProgressLayout != null) {
                mProgressLayout.showLoading();
            }
        }
        isInit = false;
    }


    @Override public void showError(String msg) {
        mIsFetching = false;
        setOnRefresh();
        if (mCurrentPage == 1 && mProgressLayout != null) {
            mProgressLayout.showError(msg, v -> fetchData(mCurrentPage));
        }
    }


    public void setOnRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.postDelayed(() -> mSwipeRefreshLayout.setRefreshing(false),
                800);
        }
    }


    @Override public void showNotData() {
        if (mProgressLayout != null) {
            mProgressLayout.showNotData(v -> fetchData(mCurrentPage));
        }
        if (mCurrentPage == 1) {
            setOnRefresh();
        }
        mIsFetching = false;
    }


    public void setData(List data) {
        if (data == null) {
            showNotData();
            return;
        }
        if (mCurrentPage == 1) {
            getAdapter().addRefreshData(data);
        } else {
            getAdapter().addLoadMoreData(data);
        }

        if (data.size() < Constants.PAGE_SIZE) {
            mRecyclerViewWithFooter.setEnd();
        } else {
            mRecyclerViewWithFooter.setPullToLoad();
        }

        RecyclerView.Adapter adapter = mRecyclerViewWithFooter.getAdapter();
        if (mCurrentPage == 1 &&
            mRecyclerViewWithFooter != null &&
            adapter != null &&
            (adapter.getItemCount() == 0
                || (adapter.getItemCount() == 1 &&
                (adapter.getItemViewType(0) == RecyclerViewWithFooter.
                    LoadMoreAdapter.LOAD_MORE_VIEW_TYPE ||
                    adapter.getItemViewType(0) == RecyclerViewWithFooter.
                        LoadMoreAdapter.EMPTY_VIEW_TYPE)))) {
            isInit = true;
            showNotData();
            return;
        }



        if (mProgressLayout != null) {
            mProgressLayout.showContent();
        }
        if (mCurrentPage == 1) {
            setOnRefresh();
        }
        mCurrentPage++;
        mIsFetching = false;
    }


    @Override public Context context() {
        return getActivity();
    }


    protected abstract void fetchData(int currentPageIndex);


    protected abstract BaseListAdapter getAdapter();

    protected abstract RecyclerView.LayoutManager getLayoutManager();


}
