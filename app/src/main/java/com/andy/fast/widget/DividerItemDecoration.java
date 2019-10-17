package com.andy.fast.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by leeandy007 on 2016/12/16.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    private static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private int mSpace = 1;     //间隔
    private Rect mRect = new Rect(0, 0, 0, 0);
    private Paint mPaint = new Paint();

    private int mOrientation;

    private int mMargin;

    private static Context mContext;

    private DividerItemDecoration(Context context, int orientation, @ColorInt int color, int space, int margin) {
        mContext = context;
        mOrientation = orientation;
        mMargin = margin;
        if (space > 0) {
            mSpace = space;
        }
        mPaint.setColor(color);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + dip2px(mMargin);
        final int right = parent.getWidth() - parent.getPaddingRight() - dip2px(mMargin);

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mSpace;
            mRect.set(left, top, right, bottom);
            c.drawRect(mRect, mPaint);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop() + dip2px(mMargin);
        final int bottom = parent.getHeight() - parent.getPaddingBottom() - dip2px(mMargin);

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mSpace;
            mRect.set(left, top, right, bottom);
            c.drawRect(mRect, mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mSpace);
        } else {
            outRect.set(0, 0, mSpace, 0);
        }
    }

    public static DividerItemDecoration createVertical(Context context, @ColorInt int color, int height, int margin) {
        return new DividerItemDecoration(context, VERTICAL_LIST, color, height, margin);
    }

    public static DividerItemDecoration createHorizontal(Context context, @ColorInt int color, int width, int margin) {
        return new DividerItemDecoration(context, HORIZONTAL_LIST, color, width, margin);
    }

    /**
     * DIP -> PX 转换
     *
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
