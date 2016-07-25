package com.wkw.hot.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wkw.hot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wukewei on 16/6/1.
 */
public class ProgressLayout extends RelativeLayout {

    private static final String LOADING_TAG = "loading_tag";
    private static final String ERROR_TAG = "error_tag";
    private static final String NOTDATA_TAG = "not_data_tag";


    private LayoutParams layoutParams;
    private LayoutInflater layoutInflater;
    private RelativeLayout loadingView, errorView, notDataView;

    private Button btn_error, btn_notdata;

    private List<View> contentViews = new ArrayList<>();
    public ProgressLayout(Context context) {
        super(context);
    }

    private enum State {
        LOADING, CONTENT, ERROR, NOTDATA
    }
    private State currentState = State.LOADING;

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getTag() == null || (!child.getTag().equals(LOADING_TAG) && !child.getTag().equals(ERROR_TAG)
                && !child.getTag().equals(NOTDATA_TAG))) {
            contentViews.add(child);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (btn_error != null) btn_error.setOnClickListener(null);
        if (btn_notdata != null) btn_error.setOnClickListener(null);
    }

    public void showLoading() {
        currentState = State.LOADING;
        this.showLoadingView();
        this.hideErrorView();
        this.hideNotDataView();
        this.setContentVisibility(false);
    }

    public void showContent() {
        currentState = State.CONTENT;
        this.hideLoadingView();
        this.hideErrorView();
        this.hideNotDataView();
        this.setContentVisibility(true);
    }

    public void showError(String msg,OnClickListener click) {
        if (this.isContent()) return;
        currentState = State.ERROR;
        this.hideNotDataView();
        this.hideLoadingView();
        this.showErrorView(msg);
        this.btn_error.setOnClickListener(click);
        this.setContentVisibility(false);
    }



    public void showNotDta(OnClickListener click) {
        currentState = State.NOTDATA;
        this.hideLoadingView();
        this.hideErrorView();
        this.showNotDataView();
        this.btn_notdata.setOnClickListener(click);
        this.setContentVisibility(false);
    }

    public boolean isContent() {
        return currentState == State.CONTENT;
    }

    private void showLoadingView() {
        if (loadingView == null) {
            loadingView = (RelativeLayout) layoutInflater.inflate(R.layout.layout_loading_view, null);
            loadingView.setTag(LOADING_TAG);
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);
            this.addView(loadingView, layoutParams);
        } else {
            loadingView.setVisibility(VISIBLE);
        }
    }

    private void showErrorView(String msg) {
        if (errorView == null) {
            errorView = (RelativeLayout) layoutInflater.inflate(R.layout.layout_error_view, null);
            errorView.setTag(ERROR_TAG);
            btn_error = (Button) errorView.findViewById(R.id.btn_try);
            ((TextView)errorView.findViewById(R.id.tv_error)).setText(msg);
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);
            this.addView(errorView, layoutParams);
        } else {
            errorView.setVisibility(VISIBLE);
        }
    }


    private void showNotDataView() {
        if (notDataView == null) {
            notDataView = (RelativeLayout) layoutInflater.inflate(R.layout.layout_no_data_view, null);
            notDataView.setTag(ERROR_TAG);
            btn_notdata = (Button) notDataView.findViewById(R.id.btn_try);
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);
            this.addView(notDataView, layoutParams);
        } else {
            notDataView.setVisibility(VISIBLE);
        }
    }


    private void hideLoadingView() {
        if (loadingView != null && loadingView.getVisibility() != GONE) {
            loadingView.setVisibility(GONE);
        }
    }

    private void hideErrorView() {
        if (errorView != null && errorView.getVisibility() != GONE) {
            errorView.setVisibility(GONE);
        }
    }


    private void hideNotDataView() {
        if (notDataView != null && notDataView.getVisibility() != GONE) {
            notDataView.setVisibility(GONE);
        }
    }

    private void setContentVisibility(boolean visible) {
        for (View contentView : contentViews) {
            contentView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

}
