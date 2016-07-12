package com.wkw.hot.data.api;

import com.wkw.hot.common.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wukewei on 16/5/26.
 */
public class HotClient {

    private final HotApi mHotApi;

    public HotClient() {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(Constants.Base_Url)
                .client(OkHttpManager.getInstance())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mHotApi = retrofit1.create(HotApi.class);
    }

    public HotApi getHotApi() {
        return mHotApi;
    }
}
