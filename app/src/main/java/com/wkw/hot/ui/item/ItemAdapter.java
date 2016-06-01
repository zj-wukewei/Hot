package com.wkw.hot.ui.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wkw.hot.R;
import com.wkw.hot.base.BaseLoadMoreAdapter;
import com.wkw.hot.entity.Popular;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukewei on 16/6/1.
 */
public class ItemAdapter extends BaseLoadMoreAdapter<Popular, ItemAdapter.ViewHolder> {



    @Override
    public void onBindItemViewHolder(ViewHolder holder,Popular data, int position) {
        Glide.with(holder.imgItem.getContext())
                .load(data.getPicUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgItem);
        holder.tvTitle.setText(data.getTitle());
        holder.tvDate.setText("来自:"+data.getDescription());
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_item)
        ImageView imgItem;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_date)
        TextView tvDate;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
