package com.andy.fast.ui.adapter.base;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.andy.fast.util.ViewUtil;
import com.andy.fast.util.bus.Bus;

import butterknife.ButterKnife;

/**
 * Created by leeandy007 on 2017/6/15.
 */

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    protected Context _context;

    protected OnItemClickListener<T> mOnItemClickListener;

    public BaseRecyclerViewHolder(Context context, int resId, ViewGroup parent, OnItemClickListener<T> onItemClickListener) {
        super(ViewUtil.createItemView(context, resId, parent));
        ButterKnife.bind(this, itemView);
        Bus.obtain().register(this);
        mOnItemClickListener = onItemClickListener;
    }

    protected void initView(View view){}

    protected void initData(Context context, final T t, final int position){
        this._context = context;
        initView(itemView);
        itemView.setOnClickListener(v -> {
            if(mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(v, t, position);
            }
        });
    };

    public void RegisterClickListener(View view, T t, int position){
        view.setOnClickListener(v -> {
            if(mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(v, t, position);
            }
        });
    }

}
