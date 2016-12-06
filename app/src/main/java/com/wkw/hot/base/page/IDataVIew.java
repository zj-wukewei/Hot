package com.wkw.hot.base.page;

import android.content.Context;
import com.wkw.hot.base.IView;

/**
 * Created by wukewei on 16/12/6.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public interface IDataVIew extends IView{
    void showLoading();
    void showContent();
    void showNotData();
    void showError(String msg);
    Context context();
}
