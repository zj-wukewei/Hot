package com.wkw.hot.entity.exception;

import com.wkw.hot.ui.App;
import com.wkw.hot.utils.NetWorkUtil;

/**
 * Created by wukewei on 16/6/1.
 */
public class ErrorHanding {

    public ErrorHanding() {
    }

    public static String handleError(Throwable throwable) {
        String message;
        if (!NetWorkUtil.isNetConnected(App.getAppContext())) {
            message = "无网络连接";
        } else if (throwable instanceof ServerException) {
            message = throwable.getMessage();
        } else {
            message = "连接服务器失败";
        }
        return message;
    }
}
