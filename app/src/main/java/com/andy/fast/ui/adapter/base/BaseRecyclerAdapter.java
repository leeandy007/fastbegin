package com.andy.fast.ui.adapter.base;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerAdapter<T> extends BaseRecyclerViewAdapter<T> {

    public BaseRecyclerAdapter(Context _context, List<T> list, ViewHolderCreator mViewHolderCreator) {
        super(_context, list, mViewHolderCreator);
    }

    public void refresh(ArrayList<T> data) {
        this._list = data;
        notifyDataSetChanged();
    }

}