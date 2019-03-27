package com.andy.fast.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class ViewUtil {

    /**
     * 创建View
     * @param context
     * @param layoutId
     * @return
     */
    public static View createView(Context context, int layoutId){
        return createView(context, layoutId, null);
    }

    /**
     * 创建View
     * @param context
     * @param layoutId
     * @return
     */
    public static View createItemView(Context context, int layoutId, ViewGroup viewGroup){
        return createView(context, layoutId, viewGroup, false);
    }

    /**
     * 创建View
     * @param context
     * @param layoutId
     * @return
     */
    public static View createView(Context context, int layoutId, ViewGroup viewGroup){
        return createView(context, layoutId, viewGroup, viewGroup != null);
    }



    /**
     * 创建View
     * @param context
     * @param layoutId
     * @param viewGroup
     * @return
     */
    public static View createView(Context context, int layoutId, ViewGroup viewGroup, boolean atta){
        return LayoutInflater.from(context).inflate(layoutId, viewGroup, atta);
    }

    /**
     * 沉浸式全屏
     * 配合Activity的 onWindowFocusChanged 方法使用
     *     @Override
     *     public void onWindowFocusChanged(boolean hasFocus) {
     *         super.onWindowFocusChanged(hasFocus);
     *         if(hasFocus){
     *             ViewUtil.fullScreen(_context);
     *         }
     *     }
     * @param context
     */
    public static void fullScreen(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = ((Activity) context).getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | //全屏，状态栏会盖在布局上
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | //隐藏导航栏
                    View.SYSTEM_UI_FLAG_FULLSCREEN | //全屏，状态栏和导航栏不显示
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public static int screenWidth(Context context){
        WindowManager manager = ((Activity)context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int screenHeight(Context context){
        WindowManager manager = ((Activity)context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

}
