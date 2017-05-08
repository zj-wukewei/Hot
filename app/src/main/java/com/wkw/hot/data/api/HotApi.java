package com.wkw.hot.data.api;

import com.wkw.common_lib.rx.ApiResponse;
import com.wkw.hot.entity.PagePopularEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wukewei on 16/5/26.
 */
public interface HotApi {

    @GET("582-2")
    Observable<ApiResponse<PagePopularEntity>> getPopular(@Query("page") int page, @Query("key") String word,
                                                          @Query("showapi_appid") String showapi_appid,
                                                          @Query("showapi_sign") String showapi_sign);

}
