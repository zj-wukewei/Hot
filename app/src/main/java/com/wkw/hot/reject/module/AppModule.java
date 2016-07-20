package com.wkw.hot.reject.module;

import com.wkw.hot.cache.CacheLoader;
import com.wkw.hot.common.Constants;
import com.wkw.hot.data.DataManager;
import com.wkw.hot.data.api.HotApi;
import com.wkw.hot.reject.ContextLife;
import com.wkw.hot.ui.App;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wukewei on 16/7/19.
 */
@Module
public class AppModule {

    private App application;

    public AppModule(App application) {
        this.application = application;
    }


    @Provides
    @Singleton
    @ContextLife("Application")
    public App provideApp() {
        return application;
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor apikey = chain -> chain.proceed(chain.request().newBuilder()
                .addHeader("apikey", Constants.Api_Key).build());

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(apikey)
                .addInterceptor(loggingInterceptor)
                .build();

        return okHttpClient;
    }

    @Provides
    @Singleton
    HotApi provideHotApi(OkHttpClient okHttpClient) {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(Constants.Base_Url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HotApi hotApi = retrofit1.create(HotApi.class);

        return hotApi;
    }

    @Provides
    @Singleton
    CacheLoader provideCacheLoader() {
        return CacheLoader.getInstance(application);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HotApi hotApi, CacheLoader cacheLoader) {
        return new DataManager(hotApi, cacheLoader);
    }


}
