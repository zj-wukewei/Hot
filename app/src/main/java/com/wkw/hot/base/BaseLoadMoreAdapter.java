package com.wkw.hot.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wkw.hot.R;
import com.wkw.hot.common.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wukewei on 16/6/1.
 */
public abstract class BaseLoadMoreAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    public static final int TYPE_FOOTER = Integer.MAX_VALUE;
    public static final int ITEM_TYPE = 0;
    private final List<T> mList = new ArrayList<>();
    private boolean hasMore = true, isLoading = true;



    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public final ProgressBar progressBar;
        public final TextView textView;
        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_view);
            textView = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            if (hasMore) {
                ((FooterViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
                ((FooterViewHolder)holder).textView.setText("正在加载...");
            } else {
                ((FooterViewHolder)holder).progressBar.setVisibility(View.GONE);
                ((FooterViewHolder)holder).textView.setText("已全部加载");
            }
        } else {
            onBindItemViewHolder((VH)holder, mList.get(position), position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_footer_view, parent, false);
            return new FooterViewHolder(view);
        } else {
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    public abstract void onBindItemViewHolder(VH holder, T data, int position);
    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return TYPE_FOOTER;
        } else {
            return ITEM_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    public void addLoadMoreData(List<T> data) {
        if (data == null) return;
        this.mList.addAll(data);
        this.hasMore = data.size() == Constants.PAGE_SIZE;
        this.isLoading = false;
        notifyDataSetChanged();
    }

    public void addRefreshData(List<T> data) {
        if (data == null) return;
        this.mList.clear();
        this.mList.addAll(data);
        this.hasMore = data.size() == Constants.PAGE_SIZE;
        this.isLoading = false;
        notifyDataSetChanged();
    }
    public boolean canLoadMore() {
        return hasMore && !isLoading;
    }

}
