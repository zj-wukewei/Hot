/*
 * The GPL License (GPL)
 *
 * Copyright (c) 2016 Moduth (https://github.com/moduth)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.wkw.common_lib.widget.loadmore;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.wkw.common_lib.R;

/**
 * @author cjj
 */
public class DefaultFootItem extends FootItem {

    private static final String TAG = "DefaultFootItem";

    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private TextView mPullToLoadText;
    private TextView mEndTextView;

    @Override
    public View onCreateView(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.rv_with_footer_loading, parent, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.rv_with_footer_loading_progress);
        mEndTextView = (TextView) view.findViewById(R.id.rv_with_footer_loading_end);
        mLoadingText = (TextView) view.findViewById(R.id.rv_with_footer_loading_load);
        mPullToLoadText = (TextView) view.findViewById(R.id.rv_with_footer_loading_pull_to_load);
        return view;
    }

    @Override
    public void onBindData(View view, int state) {
        if (state == RecyclerViewWithFooter.STATE_LOADING) {
            if (TextUtils.isEmpty(loadingText)) {
                showProgressBar(view.getContext().getResources().getString(R.string.rv_with_footer_loading));
            } else {
                showProgressBar(loadingText);
            }
        } else if (state == RecyclerViewWithFooter.STATE_END) {
            if (TextUtils.isEmpty(endText)) {
                showPullToLoad(view.getContext().getResources().getString(R.string.rv_with_footer_empty));
            } else {
                showPullToLoad(endText);
            }
        } else if (state == RecyclerViewWithFooter.STATE_PULL_TO_LOAD) {
            if (TextUtils.isEmpty(pullToLoadText)) {
                showPullToLoad(view.getContext().getResources().getString(R.string.rv_with_footer_pull_load_more));
            } else {
                showPullToLoad(pullToLoadText);
            }
        }
    }

    public void showPullToLoad(CharSequence message) {
        if (mPullToLoadText != null) {
            mPullToLoadText.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(message)) {
                mPullToLoadText.setText(message);
            }
        }
        if (mEndTextView != null) {
            mEndTextView.setVisibility(View.GONE);
        }
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        if (mLoadingText != null) {
            mLoadingText.setVisibility(View.GONE);
        }
    }

    public void showProgressBar(CharSequence load) {
        if (mEndTextView != null) {
            mEndTextView.setVisibility(View.GONE);
        }
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        if (mPullToLoadText != null) {
            mPullToLoadText.setVisibility(View.GONE);
        }
        if (mLoadingText != null) {
            if (!TextUtils.isEmpty(load)) {
                mLoadingText.setVisibility(View.VISIBLE);
                mLoadingText.setText(load);
            } else {
                mLoadingText.setVisibility(View.GONE);
            }
        }
    }

    public void showEnd(CharSequence end) {
        if (mEndTextView != null) {
            mEndTextView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(end)) {
                mEndTextView.setText(end);
            }
        }
        if (mPullToLoadText != null) {
            mPullToLoadText.setVisibility(View.GONE);
        }
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        if (mLoadingText != null) {
            mLoadingText.setVisibility(View.GONE);
        }
    }
}
