package com.wkw.common_lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;

import com.wkw.common_lib.Ext;

import java.security.InvalidParameterException;

/**
 * Created by wukewei on 16/7/24.
 */
public final class ViewUtils {
    private final static boolean DEBUG = false;

    private final static Object TAG_DECORATE = new Object();
    static private float density = -1;
    static private int screenWidth = -1;
    static private int screenHeight = -1;

    static {
        DisplayMetrics metris = Ext.getContext().getResources().getDisplayMetrics();
        density = metris.density;
        if (metris.widthPixels < metris.heightPixels) {
            screenWidth = metris.widthPixels;
            screenHeight = metris.heightPixels;
        } else {   // 部分机器使用application的context宽高反转
            screenHeight = metris.widthPixels;
            screenWidth = metris.heightPixels;
            Log.d("ViewUtils", "screenHeight:" + screenHeight + ",screenWidth:" + screenWidth);
        }
    }

    private ViewUtils() {
        // static use.
    }

    public static <T extends View> T findById(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * 防止控件被连续误点击的实用方法，传入要保护的时间，在此时间内将不可被再次点击
     */
    public static void preventViewMultipleClick(final View v, int protectionMilliseconds) {
        v.setClickable(false);
        v.setEnabled(false);
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setClickable(true);
                v.setEnabled(true);
            }
        }, protectionMilliseconds);
    }

    /**
     * Helper to set tag. Notice, do <em>NOT</em> use {@link android.view.View#setTag}
     * or {@link android.view.View#getTag} after this. Use {@link #getTag(android.view.View)} instead.
     */
    public static void setTag(View view, final Object tag) {
        setTagInternal(view, 0, tag, true);
    }

    /**
     * Helper to get tag of view.
     */
    public static Object getTag(View view) {
        return getTagInternal(view, 0);
    }

    /**
     * Helper to set tag with key-value pair. Notice, do <em>NOT</em> use {@link android.view.View#setTag}
     * or {@link android.view.View#getTag} after this. Use {@link #getTag(android.view.View, int)} instead.
     */
    public static void setTag(View view, int key, final Object tag) {
        setTagInternal(view, key, tag, false);
    }

    /**
     * Helper to get tag of specific key..
     */
    public static Object getTag(View view, int key) {
        return getTagInternal(view, key);
    }

    @SuppressWarnings("unchecked")
    private static void setTagInternal(View view, int key, final Object tag, boolean ignoreKey) {
        if (view == null) {
            return;
        }
        if (!ignoreKey) {
            // If the package id is 0x00 or 0x01, it's either an undefined package
            // or a framework id
            if ((key >>> 24) < 2) {
                throw new IllegalArgumentException("The key must be an application-specific "
                        + "resource id.");
            }
        }

        Object viewTag = view.getTag();
        if (viewTag == null || !(viewTag instanceof SparseArray)) {
            viewTag = new SparseArray<Object>();
        }
        SparseArray<Object> tagArray = (SparseArray<Object>) viewTag;
        tagArray.put(key, tag);
        view.setTag(tagArray);
    }

    @SuppressWarnings("unchecked")
    private static Object getTagInternal(View view, int key) {
        if (view == null) {
            return null;
        }
        Object viewTag = view.getTag();
        if (viewTag == null || !(viewTag instanceof SparseArray)) {
            return null;
        }
        SparseArray<Object> tagArray = (SparseArray<Object>) viewTag;
        return tagArray.get(key);
    }

    /**
     * Decorate a decor view on corresponding host view.
     *
     * @param hostView  host view to be decorated with.
     * @param decorView decor view.
     * @param gravity   gravity of decor view.
     */
    public static void decorate(View hostView, View decorView, int gravity) {
        decorate(hostView, decorView, gravity, 0, 0, 0, 0);
    }

    /**
     * Decorate a decor view on corresponding host view.
     *
     * @param hostView     host view to be decorated with.
     * @param decorView    decor view.
     * @param gravity      gravity of decor view.
     * @param leftMargin   left margin of decor view.
     * @param topMargin    top margin of decor view.
     * @param rightMargin  right margin of decor view.
     * @param bottomMargin bottom margin of decor view.
     */
    public static void decorate(View hostView, View decorView, int gravity, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        if (hostView == null || decorView == null) {
            return;
        }
        ViewParent parent = hostView.getParent();
        if (parent == null) {
            if (DEBUG) {
                throw new IllegalStateException("host " + hostView + " not attached to parent");
            }
            return;
        }
        if (!(parent instanceof ViewGroup)) {
            if (DEBUG) {
                throw new InvalidParameterException("host " + hostView + " has invalid parent " + parent);
            }
            return;
        }

        if (decorView.getParent() != null) {
            if (DEBUG) {
                throw new IllegalStateException("decorate " + decorView + " has already be added to a ViewParent " + decorView.getParent());
            }
            return;
        }

        // generate layout params, before modify the host view's layout params.
        FrameLayout.LayoutParams hostLp = generateHostLayoutParams(hostView);
        FrameLayout.LayoutParams decorLp = generateDecorLayoutParams(gravity, leftMargin, topMargin, rightMargin, bottomMargin);

        ViewGroup hostGroup = (ViewGroup) parent;
        FrameLayout decorContainer;
        if ((hostGroup instanceof FrameLayout) && getTag(hostView) == TAG_DECORATE) {
            decorContainer = (FrameLayout) hostGroup;
        } else {
            decorContainer = new DecorateContainer(hostGroup.getContext(), hostView);
            // decorate container should re-use the layout params and index as host view.
            int index = computeChildIndex(hostGroup, hostView);
            ViewGroup.LayoutParams lp = hostView.getLayoutParams();
            // decorate container should wrap it's content (host and decorate view).
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            // remove host view & add decorate container.
            hostGroup.removeView(hostView);
            hostGroup.addView(decorContainer, index, lp);
            // tag for recognise.
            setTag(decorContainer, TAG_DECORATE);
        }

        // remove all existing decorates.
        decorContainer.removeAllViews();

        // add host & decorate view.
        if (hostLp != null) {
            decorContainer.addView(hostView, hostLp);
        }
        if (decorLp != null) {
            decorContainer.addView(decorView, decorLp);
        }
    }

    private static FrameLayout.LayoutParams generateHostLayoutParams(View hostView) {
        if (hostView == null) {
            return null;
        }
        FrameLayout.LayoutParams lp = newLayoutParams(hostView.getLayoutParams());
        // host layout should contains no margin, it's container already preserves.
        lp.leftMargin = lp.rightMargin = lp.topMargin = lp.bottomMargin = 0;
        return lp;
    }

    private static FrameLayout.LayoutParams generateDecorLayoutParams(int gravity, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = gravity;
        lp.leftMargin = leftMargin;
        lp.topMargin = topMargin;
        lp.rightMargin = rightMargin;
        lp.bottomMargin = bottomMargin;
        return lp;
    }

    private static FrameLayout.LayoutParams newLayoutParams(ViewGroup.LayoutParams source) {
        if (source == null) {
            return null;
        }
        if (source instanceof ViewGroup.MarginLayoutParams) {
            return new FrameLayout.LayoutParams((ViewGroup.MarginLayoutParams) source);
        } else {
            return new FrameLayout.LayoutParams(source);
        }
    }

    private static int computeChildIndex(ViewGroup parent, View child) {
        if (parent == null || child == null) {
            return -1;
        }
        int index = 0;
        int count = parent.getChildCount();
        for (; index < count; index++) {
            if (parent.getChildAt(index) == child) {
                break;
            }
        }
        return (index >= 0 && index < count) ? index : -1;
    }

    /**
     * Capture corresponding view.
     *
     * @param view view to capture.
     */
    public static Bitmap capture(View view) {
        view.buildDrawingCache();
        Bitmap drawingCache = view.getDrawingCache();
        if (drawingCache == null) {
            return null;
        }
        Context context = view.getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        Bitmap bitmap = Bitmap.createBitmap(drawingCache, 0, 0,
                Math.min(outMetrics.widthPixels, drawingCache.getWidth()),
                Math.min(outMetrics.heightPixels, drawingCache.getHeight()));
        if (!view.isDrawingCacheEnabled()) {
            view.destroyDrawingCache();
        }
        return bitmap;
    }

    // 支持系统设置的字体大中小变化
    public static float getSpValue(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value,
                Ext.getContext().getResources().getDisplayMetrics());

    }

    public static boolean isChildOf(View c, View p) {
        if (c == p) {
            return true;
        } else if (p instanceof ViewGroup) {
            int count = ((ViewGroup) p).getChildCount();
            for (int i = 0; i < count; i++) {
                View ci = ((ViewGroup) p).getChildAt(i);
                if (isChildOf(c, ci)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void getChildPos(View child, View parent, int[] posContainer) {
        if (posContainer == null || posContainer.length < 2) {
            return;
        }
        int x = 0;
        int y = 0;
        View vc = child;
        while (vc.getParent() != null) {
            x += vc.getLeft();
            y += vc.getTop();
            if (vc.getParent() == parent) {
                posContainer[0] = x;
                posContainer[1] = y;
                if (posContainer.length >= 4) {
                    posContainer[2] = vc.getMeasuredWidth();
                    posContainer[3] = vc.getMeasuredHeight();
                }
                break;
            }
            try {
                vc = (View) vc.getParent();
                if (posContainer.length >= 4) {
                    posContainer[2] = vc.getMeasuredWidth();
                    posContainer[3] = vc.getMeasuredHeight();
                }
            } catch (ClassCastException e) {
                break;
            }
        }
        if (parent == null) {
            posContainer[0] = x;
            posContainer[1] = y;
        }
    }

    public static String getActivityName(Context ctx) {
        Context c = ctx;
        if (!(ctx instanceof Activity) && (ctx instanceof ContextWrapper)) {
            c = ((ContextWrapper) ctx).getBaseContext();
        }
        return c.getClass().getName();
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static float getDensity() {
        return density;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Converts a dp value to a px value
     *
     * @param dp the dp value
     */
    public static int dpToPx(float dp) {
        return Math.round(dp * getDensity());
    }

    public static int pxToDp(float px) {
        return Math.round(px / getDensity());
    }

    public static int spToPx(float sp) {
        final float fontScale = Ext.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    public static int pxTosp(float px) {
        final float fontScale = Ext.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    /**
     * 在xml里设置android:alpha对api11以前的系统不起作用，所以在代码里获取并设置透明度
     */
    public static void setAlpha(View view, float alphaValue) {
        if (view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setAlpha(alphaValue);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(alphaValue, alphaValue);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            view.startAnimation(alpha);
        }
    }

    /**
     * 兼容4.1前后的设置view background drawable的方法
     */
    public static void setViewBackground(View v, Drawable drawable) {
        if (v == null) {
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackgroundDrawable(drawable);
        } else {
            v.setBackground(drawable);
        }
    }

    private static class DecorateContainer extends FrameLayout {

        private final View mHostView;

        public DecorateContainer(Context context, View hostView) {
            super(context);
            mHostView = hostView;
        }

        @Override
        public int getVisibility() {
            // decorate container share the visibility with host view.
            return mHostView != null ? mHostView.getVisibility() : super.getVisibility();
        }
    }
}
