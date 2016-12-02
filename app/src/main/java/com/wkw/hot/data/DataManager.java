package com.wkw.hot.data;

import com.wkw.hot.cache.CacheLoader;
import com.wkw.hot.cache.NetworkCache;
import com.wkw.hot.common.Constants;
import com.wkw.hot.data.api.HotApi;
import com.wkw.hot.entity.ListPopularEntity;
import com.wkw.hot.entity.PopularEntity;
import com.wkw.common_lib.rx.RxResultHelper;
import com.wkw.common_lib.rx.SchedulersCompat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wukewei on 16/7/12.
 * 这个类是管理app的数据来源无论从网络获取.内存.还是磁盘
 */
public class DataManager {

    private  HotApi mHotApi;
    private  CacheLoader cacheLoader;


    @Inject
    public DataManager(HotApi hotApi, CacheLoader cacheLoader) {
        this.mHotApi = hotApi;
        this.cacheLoader = cacheLoader;
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
    public Observable<List<PopularEntity>> getPopular(int pn, String type) {
        return mHotApi.getPopular(pn, Constants.PAGE_SIZE, type)
                .compose(SchedulersCompat.applyIoSchedulers())
                .compose(RxResultHelper.handleResult())
                .doOnNext(populars -> {
                    if (pn == 1) {
                        ListPopularEntity popular = new ListPopularEntity(populars);
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
    public Observable<List<PopularEntity>> getCachePopular(String type) {
        NetworkCache<ListPopularEntity> networkCache = new NetworkCache<ListPopularEntity>() {
            @Override
            public Observable<ListPopularEntity> get(String key, Class<ListPopularEntity> cls) {
                return getPopular(1, type)
                        .flatMap(populars -> {
                            ListPopularEntity popular = new ListPopularEntity(populars);
                            return Observable.create(new Observable.OnSubscribe<ListPopularEntity>() {
                                @Override
                                public void call(Subscriber<? super ListPopularEntity> subscriber) {
                                    if (subscriber.isUnsubscribed())
                                        return;
                                    subscriber.onNext(popular);
                                    subscriber.onCompleted();
                                }
                            });
                        });
            }
        };

        return cacheLoader.asDataObservable(Constants.NEW_LIST + type, ListPopularEntity.class, networkCache)
                .map(listPopular -> listPopular.data);
    }
}
