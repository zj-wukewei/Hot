package com.wkw.common_lib.image;

import android.content.Context;
import com.wkw.common_lib.image.glide.GlideImageConfig;

import static com.wkw.common_lib.image.glide.GlideImageConfig.*;

/**
 * Created by wukewei on 16/12/22.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public class ImageLoader {

    private ImageLoaderStrategy mImageLoaderStrategy;

    public ImageLoader() {
    }


    public void setImageLoaderStragety(ImageLoaderStrategy imageLoaderStrategy) {
        this.mImageLoaderStrategy = imageLoaderStrategy;
    }


    public <T extends ImageConfig> void  displayImage(Context context, T config) {
        this.mImageLoaderStrategy.loadImage(context, config);
    }



    public static ImageLoader getInstance() {
        return ImageLoaderInstance.mImageLoader;
    }


    public static class ImageLoaderInstance {
        private final static ImageLoader mImageLoader = new ImageLoader();
    }

}
