package com.wkw.hot.data.api;

import com.wkw.hot.entity.ApiResponse;
import com.wkw.hot.entity.Popular;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wukewei on 16/5/26.
 */
public interface HotApi {

    @GET("txapi/weixin/wxhot")
    rx.Observable<ApiResponse<List<Popular>>> getPopular(@Query("page") int page, @Query("num") int num, @Query("word") String word);

}
