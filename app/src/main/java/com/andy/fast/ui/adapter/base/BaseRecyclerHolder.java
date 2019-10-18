package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.view.View;


public abstract class BaseRecyclerHolder<T> extends BaseRecyclerViewHolder<T> {

    protected View mView;

    protected Context _context;

    protected OnItemClickListener<T> mOnItemClickListener;

    public BaseRecyclerHolder(View view, OnItemClickListener<T> onItemClickListener) {
        super(view);
        mView = view;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void initView(View view) {
        mView = view;
    }

    @Override
    public void initData(Context context, final T t, final int position) {
        this._context = context;
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v, t, position);
                }
            }
        });
    }
}
