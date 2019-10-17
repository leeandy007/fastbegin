package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andy.fast.util.bus.Bus;

import butterknife.ButterKnife;

/**
 * Created by leeandy007 on 2017/6/15.
 */

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        Bus.obtain().register(this);
        initView(view);
    }

    public void initView(View view){}

    public abstract void initData(Context context, T t, int postion);

}
