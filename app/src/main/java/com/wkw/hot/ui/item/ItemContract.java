package com.wkw.hot.ui.item;

import com.wkw.hot.base.IPresenter;
import com.wkw.hot.base.IView;

/**
 * Created by wukewei on 16/5/30.
 */
public class ItemContract {
    interface View extends IView {

    }
    interface Presenter extends IPresenter {
        void getListData(String type);
    }
}
