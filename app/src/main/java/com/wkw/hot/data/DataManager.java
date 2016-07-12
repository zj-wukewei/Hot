package com.wkw.hot.data;

import android.os.Handler;
import android.os.HandlerThread;

import com.google.gson.Gson;
import com.wkw.hot.cache.CacheLoader;
import com.wkw.hot.cache.NetworkCache;
import com.wkw.hot.common.Constants;
import com.wkw.hot.data.api.HotApi;
import com.wkw.hot.data.api.HotFactory;
import com.wkw.hot.entity.ListPopular;
import com.wkw.hot.entity.Popular;
import com.wkw.hot.ui.App;
import com.wkw.hot.utils.RxBus;
import com.wkw.hot.utils.RxResultHelper;
import com.wkw.hot.utils.SchedulersCompat;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by wukewei on 16/7/12.
 * 这个类是管理app的数据来源无论从网络获取.内存.还是磁盘
 */
public class DataManager {

    private static Gson gson;
    private static RxBus rxBus;
    private static HotApi mHotApi;
    private static CacheLoader cacheLoader;

    private Handler mHandler;
    private DataManager() {}
    public static DataManager getInstance() {
        return DataManagerHolder.INSTANCE;
    }


    public static class DataManagerHolder {
        private final  static DataManager INSTANCE = new DataManager();
    }

    public void initService() {
        gson = new Gson();
        rxBus = RxBus.getDefault();
        cacheLoader = CacheLoader.getInstance(App.getAppContext());
        HandlerThread ioThread = new HandlerThread("IoThread");
        ioThread.start();
        mHandler = new Handler(ioThread.getLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mHotApi = HotFactory.getHotApi();
            }
        });
    }


    /***
     * 获取分类的类型
     * @param
     * @param
     * @return
     */
    public List<String> getTabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add("科技");
        tabs.add("美女");
        tabs.add("生活");
        tabs.add("娱乐");
        tabs.add("搞笑");
        tabs.add("宅男");
        return tabs;
    }

    /***
     * 获取列表
     * @param pn 页码
     * @param type 类别名称
     * @return
     */
    public Observable<List<Popular>> getPopular(int pn, String type) {
        return mHotApi.getPopular(pn, Constants.PAGE_SIZE, type)
                .compose(SchedulersCompat.applyIoSchedulers())
                .compose(RxResultHelper.handleResult())
                .doOnNext(populars -> {
                    if (pn == 1) {
                        ListPopular popular = new ListPopular(populars);
                        cacheLoader.upNewData(type, popular);
                    }
                });
    }

    /***
     * 获取缓存信息 默认只缓存第一页
     * @param type 类别名称
     * @param
     * @return
     */
    public Observable<List<Popular>> getCachePopular(String type) {
        NetworkCache<ListPopular> networkCache = new NetworkCache<ListPopular>() {
            @Override
            public Observable<ListPopular> get(String key, Class<ListPopular> cls) {
                return mHotApi.getPopular(1, Constants.PAGE_SIZE, type)
                        .compose(SchedulersCompat.applyIoSchedulers())
                        .compose(RxResultHelper.handleResult())
                        .flatMap(populars -> {
                            ListPopular popular = new ListPopular(populars);
                            return Observable.just(popular);
                        });
            }
        };

        return cacheLoader.asDataObservable(Constants.NEW_LIST + type, ListPopular.class, networkCache)
                .map(listPopular -> listPopular.data);
    }
}
