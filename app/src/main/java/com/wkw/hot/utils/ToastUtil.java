package com.wkw.hot.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wukewei on 16/5/30.
 */
public class ToastUtil {

    public static void showShort(Context context, String msg) {
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String msg) {
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
    }
}
