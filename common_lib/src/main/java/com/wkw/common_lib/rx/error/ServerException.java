package com.wkw.common_lib.rx.error;

/**
 * Created by wukewei on 16/5/30.
 */
public class ServerException extends Exception {


    private int mCode;
    public ServerException(int code,String msg) {
        super(msg);
        this.mCode = code;
    }

    /***
     * 这里可以获取code来判断具体是什么错误
     * @param
     * @param
     * @return
     */
    public int getCode() {
        return mCode;
    }
}
