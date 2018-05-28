package com.sunfusheng.dialog.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Collection;
import java.util.Map;

/**
 * @author sunfusheng on 2018/5/25.
 */
public class Utils {

    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return null == array || array.length == 0;
    }

    public static int getSize(Collection<?> collection) {
        return null == collection ? 0 : collection.size();
    }

    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        assert windowManager != null;
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
}
