package com.wkw.hot.base;

/**
 * Created by wukewei on 16/5/26.
 */
public interface IPresenter<T extends IView> {
    void attachView(T view);
    void detachView();
}
