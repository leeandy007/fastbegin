package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.view.View;

import com.andy.fast.util.bus.Bus;

import butterknife.ButterKnife;

/**
 * Created by leeandy007 on 2017/6/15.
 */

public abstract class BaseInfoViewHolder<T> {

    public View CreateView(Context context){
        View view = View.inflate(context, getLayout(), null);
        ButterKnife.bind(this, view);
        Bus.obtain().register(this);
        return view;
    }

    public abstract int getLayout();

    public void initView(View view){}

    public abstract void initData(Context context, T t, int position);

}
