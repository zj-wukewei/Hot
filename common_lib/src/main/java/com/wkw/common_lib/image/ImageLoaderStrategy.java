package com.wkw.common_lib.image;

import android.content.Context;

/**
 * Created by wukewei on 16/12/22.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public interface ImageLoaderStrategy<T extends ImageConfig> {
    void loadImage(Context context, T t);
}
