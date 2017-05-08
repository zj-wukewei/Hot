package com.wkw.common_lib.rx.error;

import com.wkw.common_lib.Ext;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by wukewei on 16/6/1.
 */
public class ErrorHanding {

    public ErrorHanding() {
    }

    public static String handleError(ErrorBundle e) {
        e.getException().printStackTrace();
        String message;
        Exception exception = e.getException();
        if (!Ext.g().isAvailable()) {
            message = "网络中断，请检查您的网络状态";
        } else if (exception instanceof SocketTimeoutException) {
            message = "网络中断，请检查您的网络状态";
        } else if (exception instanceof ConnectException) {
            message = "网络中断，请检查您的网络状态";
        } else if (exception instanceof NetworkConnectionException) {
            message = "网络中断，请检查您的网络状态";
        } else if (exception instanceof ServerException) {
            int code  = ((ServerException) exception).getCode();
            //在这里你可以获取code来判断是什么类型  好比有些token失效了你就可以实现跳转到登录页面
            message = e.getErrorMessage();
        } else {
            message = "连接服务器失败,请稍后再试";
        }
        return message;
    }

}
