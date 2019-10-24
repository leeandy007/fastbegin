package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by leeandy007 on 16/9/18.
 */
public class BaseRecyclerAdapter<T> extends BaseRecyclerViewAdapter<T> {

    public BaseRecyclerAdapter(Context _context, List<T> list, ViewHoldersCreator mViewHoldersCreator) {
        super(_context, list, mViewHoldersCreator);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (BaseRecyclerViewHolder) mViewHoldersCreator.createHolder(viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return mViewHoldersCreator.initViewType(position);
    }

}
