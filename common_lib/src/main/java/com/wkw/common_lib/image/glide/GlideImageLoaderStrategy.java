package com.wkw.common_lib.image.glide;

import android.app.Activity;
import android.content.Context;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wkw.common_lib.image.ImageLoaderStrategy;

/**
 * Created by wukewei on 16/12/22.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public class GlideImageLoaderStrategy implements ImageLoaderStrategy<GlideImageConfig> {

    @Override public void loadImage(Context context, GlideImageConfig imageConfig) {
        RequestManager manager;
        if (context instanceof Activity) {
            manager = Glide.with((Activity) context);
        } else {
            manager = Glide.with(context);
        }

        DrawableRequestBuilder<String> request = manager.load(imageConfig.getUrl())
            .crossFade()
            .centerCrop();
        switch (imageConfig.getCacheStrategy()) {
            case 0:
                request.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                request.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                request.diskCacheStrategy(DiskCacheStrategy.SOURCE);
                break;
            case 3:
                request.diskCacheStrategy(DiskCacheStrategy.RESULT);
                break;
            default:
                break;
        }

        if (imageConfig.getPlaceholder() != 0) {
            request.placeholder(imageConfig.getPlaceholder());
        }
        if (imageConfig.getErrorPic() != 0) {
            request.error(imageConfig.getErrorPic());
        }

        request.into(imageConfig.getImageView());
    }
}
