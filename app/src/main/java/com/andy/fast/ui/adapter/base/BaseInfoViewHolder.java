package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.view.View;

import com.andy.fast.util.bus.Bus;

/**
 * Created by leeandy007 on 2017/6/15.
 */

public abstract class BaseInfoViewHolder<T> {

    public View CreateView(Context context){
        View view = View.inflate(context, getLayout(), null);
        Bus.obtain().register(this);
        return view;
    }

    protected abstract int getLayout();

    protected void initView(View view){}

    protected abstract void initData(Context context, T t, int position);

}
