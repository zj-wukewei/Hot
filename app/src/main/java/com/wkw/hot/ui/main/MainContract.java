package com.wkw.hot.ui.main;

import com.wkw.hot.base.IPresenter;
import com.wkw.hot.base.IView;

import java.util.List;

/**
 * Created by wukewei on 16/5/26.
 */
public interface MainContract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter {
        void getTabs(List<String> tabs);
    }
}
