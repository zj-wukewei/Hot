package com.wkw.common_lib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by wukewei on 16/12/4.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */

public class TimerButton extends Button {

    private static final long UNIT = 1000;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private long mDuration;
    private String mNormalText;
    private String mTimerTextFormat;
    private Drawable mEnableBackground;
    private Drawable mDisableBackground;
    private Runnable mTimerRunable = new Runnable() {
        @Override public void run() {
            if (mDuration <= 0) {
                setText(mNormalText);
                setEnabled(true);
                return;
            }
            setText(String.format(mTimerTextFormat, mDuration / UNIT));
            mDuration -= UNIT;
            mHandler.postDelayed(mTimerRunable, UNIT);
        }
    };


    public TimerButton(Context context) {
        super(context);
    }


    public TimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimerButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void startTimer(String timerTextFormat,String normalText, long duration) {
        mTimerTextFormat = timerTextFormat;
        mNormalText = normalText;
        mDuration = duration;
        setEnabled(false);
        mHandler.post(mTimerRunable);
    }

    public void setDisableBackground(Drawable disableBackground) {
        mEnableBackground = getBackground();
        mDisableBackground = disableBackground;
    }

    private void setEnableStatus(boolean enable) {
        setEnabled(enable);
        if (enable) {
            setBackgroundDrawable(mEnableBackground);
        } else {
            setBackgroundDrawable(mDisableBackground);
        }
    }


}
