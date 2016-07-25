package com.wkw.hot.data;

import com.wkw.hot.cache.CacheLoader;
import com.wkw.hot.cache.NetworkCache;
import com.wkw.hot.common.Constants;
import com.wkw.hot.data.api.HotApi;
import com.wkw.hot.entity.ListPopular;
import com.wkw.hot.entity.Popular;
import com.wkw.hot.utils.RxResultHelper;
import com.wkw.hot.utils.SchedulersCompat;

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
                return getPopular(1, type)
                        .flatMap(populars -> {
                            ListPopular popular = new ListPopular(populars);
                            return Observable.create(new Observable.OnSubscribe<ListPopular>() {
                                @Override
                                public void call(Subscriber<? super ListPopular> subscriber) {
                                    if (subscriber.isUnsubscribed())
                                        return;
                                    subscriber.onNext(popular);
                                    subscriber.onCompleted();
                                }
                            });
                        });
            }
        };

        return cacheLoader.asDataObservable(Constants.NEW_LIST + type, ListPopular.class, networkCache)
                .map(listPopular -> listPopular.data);
    }
}
