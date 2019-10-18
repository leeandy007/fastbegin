package com.andy.fast.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.andy.fast.R;
import com.andy.fast.widget.DividerItemDecoration;
import com.andy.fast.widget.MarginDecoration;

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

    public enum Model{
        VERTICAL,
        HORIZONTAL
    }



    public static void initList(Context context, RecyclerView recyclerView, Model model, int margin){
        initList(context, recyclerView, model, margin, 1, R.color.gray);
    }

    public static void initList(Context context, RecyclerView recyclerView, Model model, int margin, int space){
        initList(context, recyclerView, model, margin, space, R.color.gray);
    }

    public static void initList(Context context, RecyclerView recyclerView, Model model, int margin, int space, int color){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        DividerItemDecoration dividerItemDecoration = null;
        switch (model) {
            case HORIZONTAL:
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                dividerItemDecoration = DividerItemDecoration.createHorizontal(context, context.getResources().getColor(color), space, margin);
                break;
            case VERTICAL:
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                dividerItemDecoration = DividerItemDecoration.createVertical(context, context.getResources().getColor(color), space, margin);
                break;
        }
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public static void initGrid(Context context, RecyclerView recyclerView, int row){
        recyclerView.setLayoutManager(new GridLayoutManager(context, row));
        recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen.item_margin));
    }

    public static void initStaggered(Context context, RecyclerView recyclerView, int row){
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(row, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new MarginDecoration(context, R.dimen.item_margin));
    }


}
