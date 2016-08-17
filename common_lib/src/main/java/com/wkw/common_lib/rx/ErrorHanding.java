package com.wkw.common_lib.rx;

import com.wkw.common_lib.Ext;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by wukewei on 16/6/1.
 */
public class ErrorHanding {

    public ErrorHanding() {
    }

    public static String handleError(Throwable e) {
        e.printStackTrace();
        String message;
        if (!Ext.g().isAvailable()) {
            message = "网络中断，请检查您的网络状态";
        } else if (e instanceof SocketTimeoutException) {
            message = "网络中断，请检查您的网络状态";
        } else if (e instanceof ConnectException) {
            message = "网络中断，请检查您的网络状态";
        } else if (e instanceof ServerException) {
            message = e.getMessage();
        } else {
            message = "连接服务器失败,请稍后再试";
        }
        return message;
    }

}
