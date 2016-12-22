package com.wkw.common_lib.image.glide;

import android.widget.ImageView;
import com.wkw.common_lib.image.ImageConfig;

/**
 * Created by wukewei on 16/12/22.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public class GlideImageConfig extends ImageConfig {
    private int cacheStrategy;


    public GlideImageConfig(Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
        this.cacheStrategy = builder.cacheStrategy;
    }


    public int getCacheStrategy() {
        return cacheStrategy;
    }


    public void setCacheStrategy(int cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String url;
        private ImageView imageView;
        private int placeholder;
        private int errorPic;
        private int cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT

        private Builder() {
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder errorPic(int errorPic){
            this.errorPic = errorPic;
            return this;
        }

        public Builder imagerView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder cacheStrategy(int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }


        public GlideImageConfig build() {
            if (url == null) throw new IllegalStateException("url is required");
            if (imageView == null) throw new IllegalStateException("imageview is required");
            return new GlideImageConfig(this);
        }
    }
}
