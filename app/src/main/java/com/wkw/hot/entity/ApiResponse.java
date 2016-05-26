package com.wkw.hot.entity;

/**
 * Created by wukewei on 16/5/26.
 */
public class ApiResponse<T> {
    public static final int SUCCESS_CODE = 200;

    private int code;
    private String msg;
    private T newsList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getNewsList() {
        return newsList;
    }

    public void setNewsList(T newsList) {
        this.newsList = newsList;
    }

    public boolean isSuccess() {
        if (this.code == SUCCESS_CODE) {
            return true;
        } else {
            return false;
        }
    }
}
