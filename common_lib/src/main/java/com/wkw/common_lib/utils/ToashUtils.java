package com.wkw.common_lib.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by wukewei on 16/7/24.
 */
public final class ToashUtils {
    private static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    private static final int LENGTH_LONG = Toast.LENGTH_LONG;

    private final static int DEFAULT_GRAVITY = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

    private final static Singleton<Toast, Context> sToast = new Singleton<Toast, Context>() {
        @Override
        protected Toast create(Context context) {
            if (context == null || context.getApplicationContext() == null) {
                return null;
            }
            try {
                return Toast.makeText(context.getApplicationContext(), null, LENGTH_SHORT);
            } catch (Throwable e) {
                return null;
            }
        }
    };

    private ToashUtils() {

    }

    /**
     * Get the singleton instance of toast.
     *
     * @param context Application or activity context.
     * @return Singleton instance of toast.
     */
    public static Toast get(Context context) {
        return sToast.get(context);
    }

    /**
     * Show application or activity level toast.
     *
     * @param context Application or activity context.
     * @param resId   The resource text to show.  Can be formatted text.
     */
    public static void show(Context context, int resId) {
        show(context, resId, LENGTH_SHORT);
    }

    /**
     * Show application or activity level toast.
     *
     * @param context Application or activity context.
     * @param msg     The text to show.  Can be formatted text.
     */
    public static void show(Context context, CharSequence msg) {
        show(context, msg, LENGTH_SHORT);
    }

    /**
     * Show application or activity level toast.
     *
     * @param context  Application or activity context.
     * @param resId    The resource text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link Toast#LENGTH_SHORT} or
     *                 {@link Toast#LENGTH_LONG}
     */
    public static void show(Context context, int resId, int duration) {
        show(context, resId, duration, DEFAULT_GRAVITY);
    }

    /**
     * Show application or activity level toast.
     *
     * @param context  Application or activity context.
     * @param msg      The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link Toast#LENGTH_SHORT} or
     *                 {@link Toast#LENGTH_LONG}
     */
    public static void show(Context context, CharSequence msg, int duration) {
        show(context, msg, duration, DEFAULT_GRAVITY);
    }

    /**
     * Show application or activity level toast.
     *
     * @param context  Application or activity context.
     * @param resId    The resource text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link Toast#LENGTH_SHORT} or
     *                 {@link Toast#LENGTH_LONG}
     * @param gravity  The gravity when display the message. See constant defined in {@link Gravity}.
     */
    public static void show(Context context, int resId, int duration, int gravity) {
        show(context, resId == 0 ? null : getString(context, resId), duration, gravity);
    }

    /**
     * Show application or activity level toast.
     *
     * @param context  Application or activity context.
     * @param msg      The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link Toast#LENGTH_SHORT} or
     *                 {@link Toast#LENGTH_LONG}
     * @param gravity  The gravity when display the message. See constant defined in {@link Gravity}.
     */
    public static void show(Context context, final CharSequence msg, final int duration, final int gravity) {
        if (msg == null || msg.length() == 0) {
            return;
        }
        if (!shouldShow(context)) {
            return;
        }
        final Context appContext = context.getApplicationContext();
        if (ThreadUtils.isMainThread()) {
            showImmediately(appContext, msg, duration, gravity);
        } else {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showImmediately(appContext, msg, duration, gravity);
                }
            });
        }
    }

    /**
     * Show activity level toast.
     *
     * @param activity Activity.
     * @param resId    The resource text to show.  Can be formatted text.
     */
    public static void show(Activity activity, int resId) {
        show(activity, resId, LENGTH_SHORT);
    }

    /**
     * Show activity level toast.
     *
     * @param activity Activity, can be null.
     * @param msg      The text to show.  Can be formatted text.
     */
    public static void show(@Nullable Activity activity, CharSequence msg) {
        show(activity, msg, LENGTH_SHORT);
    }

    /**
     * Show activity level toast.
     *
     * @param activity Activity.
     * @param resId    The resource text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link Toast#LENGTH_SHORT} or
     *                 {@link Toast#LENGTH_LONG}
     */
    public static void show(@Nullable Activity activity, int resId, int duration) {
        show(activity, resId == 0 ? null : getString(activity, resId), duration, DEFAULT_GRAVITY);
    }

    /**
     * Show activity level toast.
     *
     * @param activity Activity.
     * @param msg      The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link Toast#LENGTH_SHORT} or
     *                 {@link Toast#LENGTH_LONG}
     */
    public static void show(@Nullable Activity activity, CharSequence msg, int duration) {
        show(activity, msg, duration, DEFAULT_GRAVITY);
    }

    /**
     * Show activity level toast.
     *
     * @param activity Activity.
     * @param resId    The resource text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link Toast#LENGTH_SHORT} or
     *                 {@link Toast#LENGTH_LONG}
     * @param gravity  The gravity when display the message. See constant defined in {@link Gravity}.
     */
    public static void show(@Nullable Activity activity, int resId, int duration, int gravity) {
        show(activity, resId == 0 ? null : getString(activity, resId), duration, gravity);
    }

    /**
     * Show activity level toast.
     *
     * @param activity Activity.
     * @param msg      The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link Toast#LENGTH_SHORT} or
     *                 {@link Toast#LENGTH_LONG}
     * @param gravity  The gravity when display the message. See constant defined in {@link Gravity}.
     */
    public static void show(@Nullable Activity activity, final CharSequence msg,
                            final int duration, final int gravity) {
        if (msg == null || msg.length() == 0) {
            return;
        }
        if (!shouldShow(activity)) {
            return;
        }
        final Context appContext = activity.getApplicationContext();
        if (ThreadUtils.isMainThread()) {
            showImmediately(appContext, msg, duration, gravity);
        } else {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showImmediately(appContext, msg, duration, gravity);
                }
            });
        }
    }

    private static void showImmediately(Context context, CharSequence msg, int duration, int gravity) {
        Toast toast = sToast.get(context);
        if (toast != null) {
            try {
                toast.setText(msg);
                toast.setDuration(duration);
                toast.setGravity(gravity, toast.getXOffset(), toast.getYOffset());
                toast.show();
            } catch (Throwable e) {
                // internal INotificationManager may be null, just catch it and do nothing.
            }
        }
    }

    private static boolean shouldShow(Context context) {
        if (context == null) {
            // special situation.
            return true;
        }
        if (context != context.getApplicationContext()) {
            // fast way: application context.
            return true;
        }
        if (context instanceof Activity) {
            return shouldShow((Activity) context);
        }
        // default.
        return true;
    }

    private static boolean shouldShow(Activity activity) {
        if (activity == null) {
            // null activity.
            return false;
        }
        if (activity.isFinishing()) {
            // activity finished.
            return false;
        }
        Window window = activity.getWindow();
        if (window == null) {
            // contains no window.
            return false;
        }
        View decorView = window.getDecorView();
        if (decorView == null || decorView.getVisibility() != View.VISIBLE) {
            // contains no decor view or decor view is not visible.
            return false;
        }
        return true;
    }

    private static String getString(Context context, int resId) {
        return context.getString(resId);
    }


}
