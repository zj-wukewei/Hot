package com.wkw.hot.base.page;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wukewei on 16/12/6.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public abstract class BaseListAdapter<MODEL> extends RecyclerView.Adapter {

    protected List<MODEL> mData = new ArrayList<MODEL>();


    @Override public int getItemCount() {
        return mData.size();
    }

    public void addLoadMoreData(List<MODEL> data) {
        if (data == null) return;
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addRefreshData(List<MODEL> data) {
        if (data == null) return;
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }
}
