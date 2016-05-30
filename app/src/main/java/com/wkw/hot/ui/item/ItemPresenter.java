package com.wkw.hot.ui.item;

import android.app.Activity;
import android.util.Log;

import com.wkw.hot.base.BasePresenter;
import com.wkw.hot.common.Constants;
import com.wkw.hot.utils.RxResultHelper;
import com.wkw.hot.utils.SchedulersCompat;

/**
 * Created by wukewei on 16/5/30.
 */
public class ItemPresenter extends BasePresenter<ItemContract.View> implements ItemContract.Presenter {

    protected int pn = 1;

    protected void replacePn() {
        pn = 1;
    }
    public ItemPresenter(Activity activity, ItemContract.View view) {
        super(activity, view);
    }

    @Override
    public void getListData(String type) {
       mSubscription = mHotApi.getPopular(this.pn, Constants.PAGE_SIZE, type)
               .compose(SchedulersCompat.applyIoSchedulers())
               .compose(RxResultHelper.handleResult())
               .subscribe(populars -> {
                   Log.d("aaa",populars.get(0).toString());
               }, throwable -> {
                   handleError(throwable);
               });

    }
}
