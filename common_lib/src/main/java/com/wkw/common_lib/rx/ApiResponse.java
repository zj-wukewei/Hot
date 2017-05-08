package com.wkw.common_lib.rx;

/**
 * Created by wukewei on 16/5/26.
 */
public class ApiResponse<T> {

    public static final int SUCCESS_CODE = 0;

    private int showapi_res_code;
    private String showapi_res_error;
    private T showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public T getNewsList() {
        return showapi_res_body;
    }

    public void setNewsList(T newsList) {
        this.showapi_res_body = newsList;
    }

    public boolean isSuccess() {
        if (this.showapi_res_code == SUCCESS_CODE) {
            return true;
        } else {
            return false;
        }
    }
}
