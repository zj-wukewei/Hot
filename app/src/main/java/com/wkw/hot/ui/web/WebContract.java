package com.wkw.hot.ui.web;

import com.wkw.hot.base.IPresenter;
import com.wkw.hot.base.IView;

/**
 * Created by wukewei on 16/5/30.
 */
public class WebContract {
    interface View extends IView {
    }
    interface Presenter extends IPresenter<View> {
    }
}
