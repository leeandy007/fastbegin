package com.andy.fast.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.andy.fast.R;
import com.andy.fast.widget.DividerItemDecoration;
import com.andy.fast.widget.MarginDecoration;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

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
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int screenHeight(Context context){
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
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

    /**
     * grid
     *
     * @param context
     * @param recyclerView
     * @param row 列数
     * @param margin
     * @return
     */
    public static void initGrid(Context context, RecyclerView recyclerView, int row, int margin){
        recyclerView.setLayoutManager(new GridLayoutManager(context, row));
        recyclerView.addItemDecoration(new MarginDecoration(context, margin));
    }

    /**
     * 瀑布流布局
     *
     * @param context
     * @param recyclerView
     * @param row 列数
     * @param margin
     * @return
     */
    public static void initStaggered(Context context, RecyclerView recyclerView, int row, int margin){
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(row, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new MarginDecoration(context, margin));
    }

    /**
     * DIP -> PX 转换
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * PX -> DIP 转换
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getColor(Context context, int resId){
        return context.getResources().getColor(resId);
    }

    /**
     * Tablayout的item文字宽度自适应
     *
     * @param tabLayout
     * @return
     */
    public static void setTabItemWidhSelfAdapter(TabLayout tabLayout, int margin) {
        try {
            // 拿到tabLayout的slidingTabIndicator属性
            Field slidingTabIndicatorField = tabLayout.getClass().getDeclaredField("mTabStrip");
            slidingTabIndicatorField.setAccessible(true);
            LinearLayout mTabStrip = (LinearLayout) slidingTabIndicatorField.get(tabLayout);
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);
                //拿到tabView的textView属性
                Field textViewField = tabView.getClass().getDeclaredField("textView");
                textViewField.setAccessible(true);
                TextView mTextView = (TextView) textViewField.get(tabView);
                tabView.setPadding(0, 0, 0, 0);
                // 因为想要的效果是字多宽线就多宽，所以测量mTextView的宽度
                int width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }
                // 设置tab左右间距,注意这里不能使用Padding,因为源码中线的宽度是根据tabView的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = dip2px(tabLayout.getContext(), 10);
                params.rightMargin = dip2px(tabLayout.getContext(), 10);
                tabView.setLayoutParams(params);
                tabView.invalidate();
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void updataTab(Context context, TabLayout.Tab tab, boolean isSelected){
        TextView textView = (TextView) tab.getCustomView();
        if (textView == null) {
            textView = new TextView(context);
        }
        if(isSelected){
            float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, context.getResources().getDisplayMetrics());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedSize);
            float x0 = 25, x1 = 100;
            if (textView.getText().length() == 4) {
                x0 = 50;
                x1 = 200;
            }else if(textView.getText().length() == 3){
                x0 = 40;
                x1 = 160;
            }
            Shader shader = new LinearGradient(x0, 0, x1, 0, getColor(context, R.color.gray_white), getColor(context, R.color.gray), Shader.TileMode.MIRROR);
            textView.getPaint().setShader(shader);
        } else {
            float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 18, context.getResources().getDisplayMetrics());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedSize);
            float x0 = 22, x1 = 88;
            if (textView.getText().length() == 4) {
                x0 = 44;
                x1 = 176;
            } else if(textView.getText().length() == 3){
                x0 = 33;
                x1 = 132;
            }
            Shader shader = new LinearGradient(x0, 0, x1, 0, getColor(context, R.color.gray_white), getColor(context, R.color.gray), Shader.TileMode.MIRROR);
            textView.getPaint().setShader(shader);
        }
        textView.setTextColor(getColor(context, R.color.colorPrimary));
        textView.setText(tab.getText());
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setGravity(Gravity.CENTER);
        tab.setCustomView(textView);
    }

}
