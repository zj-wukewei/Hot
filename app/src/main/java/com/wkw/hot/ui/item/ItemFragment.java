package com.wkw.hot.ui.item;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.wkw.hot.R;
import com.wkw.hot.base.BaseFragment;
import com.wkw.hot.base.IPresenter;

import butterknife.Bind;

/**
 * Created by wukewei on 16/5/30.
 */
public class ItemFragment extends BaseFragment<ItemPresenter> implements ItemContract.View{
    public static final String TYPE = "type";
    @Bind(R.id.textView)
    TextView tv;

    protected String type;
    public static ItemFragment newInstance(String type) {
        ItemFragment fragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected ItemPresenter getPresenter() {
        return new ItemPresenter(mContext, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void initEventAndData() {
        type = getArguments().getString(TYPE);
        tv.setText(type);
        mPresenter.getListData(type);
    }
}
