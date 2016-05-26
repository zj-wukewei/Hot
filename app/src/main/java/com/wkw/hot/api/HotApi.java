package com.wkw.hot.api;

import android.database.Observable;

import com.wkw.hot.entity.ApiResponse;
import com.wkw.hot.entity.Popular;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wukewei on 16/5/26.
 */
public interface HotApi {

    @GET("txapi/weixin/wxhot")
    Observable<ApiResponse<Popular>> getPopular(@Query("page") String page, @Query("word") String word);

}
