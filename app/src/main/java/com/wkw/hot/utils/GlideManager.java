package com.wkw.hot.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by wukewei on 16/6/30.
 */
public class GlideManager {

    public static void loadListImageView(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
