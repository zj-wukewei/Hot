package com.wkw.common_lib.sp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by wukewei on 16/7/24.
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Once {
    public static final int THIS_APP_INSTALL = 0;
    public static final int THIS_APP_VERSION = 1;

    private static long lastAppUpdatedTime = -1;

    private static PersistedMap tagLastSeenMap;

    private Once() {
    }

    /**
     * 初始化Once
     *
     * @param context Application context
     */
    public static void init(Context context) {
        if (tagLastSeenMap == null) {
            tagLastSeenMap = new PersistedMap(context, "TagLastSeenMap");
        }

        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            lastAppUpdatedTime = packageInfo.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException ignored) {

        }
    }

    /**
     * 检查在给定scope内对应tag是否被标记过.
     *
     * @param scope 检查范围, {@code THIS_APP_INSTALL} 或 {@code THIS_APP_VERSION}.
     * @param tag   唯一性标识该操作的字符串.
     * @return {@code true} 如果和 {@code tag} 关联的操作在给定 {@code scope} 被标记过.
     */
    public static boolean beenDone(@Scope int scope, String tag) {

        Long tagLastSeenDate = tagLastSeenMap.get(tag);

        if (tagLastSeenDate == null) {
            return false;
        }

        if (scope == THIS_APP_INSTALL) {
            return true;
        }

        return tagLastSeenDate > lastAppUpdatedTime;
    }

    /**
     * 检查某个tag是否在给定时间范围（如最近1小时）被标记过，如Once.beenDone(TimeUnit.HOURS, 1, "a")
     *
     * @param timeUnit 时间单位, 如@code TimeUnit.HOURS.
     * @param amount   时间单位的数量.
     * @param tag      唯一性标识该操作的字符串.
     * @return {@code true} 如果和 {@code tag} 关联的操作在给定最近时间段被标记过.
     */
    public static boolean beenDone(TimeUnit timeUnit, long amount, String tag) {
        long timeInMillis = timeUnit.toMillis(amount);
        return beenDone(timeInMillis, tag);
    }

    /**
     * 检查某个tag是否在给定最近时间段内被标记过
     *
     * @param timeSpanInMillis 最近时间，毫秒单位（millisecond）.
     * @param tag              唯一性标识该操作的字符串.
     * @return {@code true} 如果和 {@code tag} 关联的操作在最近X毫秒内被标记过.
     */
    public static boolean beenDone(long timeSpanInMillis, String tag) {
        Long timeTagSeen = tagLastSeenMap.get(tag);

        if (timeTagSeen == null) {
            return false;
        }

        long sinceSinceCheckTime = new Date().getTime() - timeSpanInMillis;

        return timeTagSeen > sinceSinceCheckTime;
    }

    /**
     * 标记某个tag为done.
     *
     * @param tag 唯一性标识该操作的字符串.
     */
    public static void markDone(String tag) {
        tagLastSeenMap.put(tag, new Date().getTime());
    }

    /**
     * 清掉给定tag的记录. 所有对该tag的{@link #beenDone}操作在再次标记前都会返回true.
     *
     * @param tag 唯一性标识该操作的字符串.
     */
    public static void clearDone(String tag) {
        tagLastSeenMap.remove(tag);
    }

    /**
     * 清掉所有tag的记录. 所有{@link #beenDone}操作在再次标记前都会返回true.
     */
    public static void clearAll() {
        tagLastSeenMap.clear();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({THIS_APP_INSTALL, THIS_APP_VERSION})
    public @interface Scope {
    }
}
