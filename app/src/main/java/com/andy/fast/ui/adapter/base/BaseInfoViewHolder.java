package com.andy.fast.ui.adapter.base;

import android.content.Context;

import android.view.View;

/**
 * Created by leeandy007 on 2017/6/15.
 */

public abstract class BaseInfoViewHolder<T> {

    public abstract int getLayout();

    public abstract void initView(View view);

    public abstract void initData(Context context, T t, int position);

}
