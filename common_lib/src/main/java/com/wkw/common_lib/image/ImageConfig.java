package com.wkw.common_lib.image;

import android.widget.ImageView;

/**
 * Created by wukewei on 16/12/22.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public class ImageConfig {

    protected ImageView imageView;
    protected String url;
    protected int placeholder;
    protected int errorPic;


    public String getUrl() {
        return url;
    }


    public ImageView getImageView() {
        return imageView;
    }


    public int getPlaceholder() {
        return placeholder;
    }


    public int getErrorPic() {
        return errorPic;
    }
}
