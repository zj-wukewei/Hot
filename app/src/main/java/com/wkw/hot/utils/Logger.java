package com.wkw.hot.utils;

import android.util.Log;

/**
 * Created by wukewei on 16/6/1.
 */
public class Logger {

    public static boolean isDebug = true;
    public static final String TAG = "HOT_TAG";

    public static void e(String msg) {
        if (isDebug)
            Log.d(TAG, msg);

    }

    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }
}
