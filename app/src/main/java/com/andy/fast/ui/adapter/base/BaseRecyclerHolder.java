package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.view.View;

import com.andy.fast.util.ToastUtil;


public abstract class BaseRecyclerHolder<T> extends BaseRecyclerViewHolder<T> {

    protected View mView;

    protected Context _context;

    protected OnItemClickLitener<T> mOnItemClickLitener;

    public BaseRecyclerHolder(View view) {
        super(view);
        mView = view;
    }

    public BaseRecyclerHolder(View view, OnItemClickLitener<T> onItemClickLitener) {
        super(view);
        mView = view;
        mOnItemClickLitener = onItemClickLitener;
    }

    @Override
    public void initView(View view) {
        mView = view;
    }

    public void showToast(String message){
        ToastUtil.obtain().Short(_context, message);
    }

    @Override
    public void initData(Context context, final T t, final int postion) {
        this._context = context;
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickLitener != null){
                    mOnItemClickLitener.onItemClick(v, t, postion);
                }
            }
        });
    }
}
