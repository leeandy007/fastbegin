package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by leeandy007 on 16/9/18.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected Context _context;
    protected List<T> _list;
    protected ViewHolderCreator mViewHolderCreator;

    public BaseRecyclerViewAdapter(Context _context, List<T> list, ViewHolderCreator mViewHolderCreator){
        this._context = _context;
        this._list = list;
        this.mViewHolderCreator = mViewHolderCreator;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (BaseRecyclerViewHolder) mViewHolderCreator.createHolder();
    }

    @Override
    public int getItemCount() {
        return _list.size();
    }

    public List<T> getList() {
        return _list;
    }

    public T getItem(int position){
        return _list.get(position);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.initData(_context, getItem(position), position);
    }

    public void replaceBean(int position , T t){
        _list.set(position, t);
        this.notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        _list.remove(position);
        this.notifyDataSetChanged();
    }

    public void clearAll() {
        _list.clear();
        this.notifyDataSetChanged();
    }

    public void add(List<T> beans) {
        _list.addAll(beans);
        this.notifyDataSetChanged();
    }

    public void refresh(){
        this.notifyDataSetChanged();
    }

}
