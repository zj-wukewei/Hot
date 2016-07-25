package com.wkw.common_lib.utils;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by wukewei on 16/7/25.
 */
public final class AppUtils {
    private static Bundle sOwnAppMetaInfo;

    private AppUtils() {
        // static usage.
    }

    public static boolean isForeground(Context context) {
        if (isScreenLocked(context)) {
            return false;
        }
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> a = mActivityManager.getRunningTasks(1);
        return context.getPackageName().equals(a.get(0).baseActivity.getPackageName())
                && context.getPackageName().equals(a.get(0).topActivity.getPackageName());
    }

    private static boolean isScreenLocked(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    public static int getVersionCode(Context context) {
        if (context == null) {
            return 0;
        }
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Throwable e) {
            return 0;
        }
    }

    /**
     * 获取带有指定配置信息的ApplicationInfo
     *
     * @param flags 比如 {@link android.content.pm.PackageManager#GET_META_DATA}
     * @see #getSimpleAppInfo(android.content.Context)
     * @see <a href="https://code.google.com/p/android/issues/detail?id=37968" >why this method</a>
     */
    public static ApplicationInfo getAppInfoWithFlags(Context ctx, int flags) {
        try {
            return ctx == null ? null : ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取基本的applicationInfo
     *
     * @see #getAppInfoWithFlags(android.content.Context, int)
     */
    public static ApplicationInfo getSimpleAppInfo(Context ctx) {
        return ctx == null ? null : ctx.getApplicationInfo();
    }

    /**
     * Check whether corresponding package is installed.
     *
     * @param context     Application context.
     * @param packageName Package name.
     * @return Whether corresponding package is installed.
     */
    public static boolean isPackageInstalled(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
                return packageInfo != null;
            } catch (PackageManager.NameNotFoundException e) {
                // ignore.
            }
        }
        return false;
    }

    /**
     * Get application info of own package.
     *
     * @param context Application context.
     * @return Application info of own package.
     */
    public static Bundle getApplicationMetaInfo(Context context) {
        if (sOwnAppMetaInfo == null) {
            synchronized (AppUtils.class) {
                if (sOwnAppMetaInfo == null) {
                    sOwnAppMetaInfo = getApplicationMetaInfo(context, context.getPackageName());
                }
            }
        }
        return sOwnAppMetaInfo != null ? new Bundle(sOwnAppMetaInfo) : null;
    }

    /**
     * Get application meta info of corresponding package.
     *
     * @param context     Application context.
     * @param packageName Package name.
     * @return Application meta info of corresponding package.
     */
    public static Bundle getApplicationMetaInfo(Context context, String packageName) {
        ApplicationInfo appInfo = packageName.equals(context.getPackageName()) ?
                context.getApplicationInfo() : null;
        Bundle metaInfo = appInfo != null ? appInfo.metaData : null;
        if (metaInfo == null) {
            try {
                appInfo = context.getPackageManager().getApplicationInfo(
                        packageName, PackageManager.GET_META_DATA);
                metaInfo = appInfo.metaData;
            } catch (Throwable e) {
                // ignore.
            }
        }
        return metaInfo;
    }
}
