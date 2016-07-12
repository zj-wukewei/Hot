package com.wkw.hot.data.api;

/**
 * Created by wukewei on 16/5/26.
 */
public class HotFactory {

    private static HotApi mHotApi = null;
    private static final Object WATCH = new Object();

    public HotFactory() {

    }

    public static HotApi getHotApi() {
        synchronized (WATCH) {
            if (mHotApi == null) {
                mHotApi = new HotClient().getHotApi();
            }
        }
        return mHotApi;
    }
}
