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

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.wkw.common_lib.R;

/**
 * 默认的空数据视图
 *
 * @author cjj on 16/1/31.
 */
public class DefaultEmptyItem extends EmptyItem {

    private TextView mEmptyTextView;
    private ImageView mEmptyImageView;

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_with_footer_empty_layout, parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(parent.getMeasuredWidth(), parent.getMeasuredHeight()));
        mEmptyTextView = (TextView) view.findViewById(R.id.rv_with_footer_empty_title);
        mEmptyImageView = (ImageView) view.findViewById(R.id.rv_with_footer_empty_icon);
        return view;
    }

    @Override
    public void onBindData(View view) {
        if (TextUtils.isEmpty(mEmptyText)) {
            mEmptyTextView.setVisibility(View.GONE);
        } else {
            mEmptyTextView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(mEmptyText);
        }

        if (mEmptyIconRes != -1) {
            mEmptyImageView.setImageResource(mEmptyIconRes);
        }
    }
}
