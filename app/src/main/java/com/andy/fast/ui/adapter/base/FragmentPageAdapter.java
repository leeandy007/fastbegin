package com.andy.fast.ui.adapter.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class FragmentPageAdapter<T extends Fragment> extends FragmentStateAdapter {

    protected Context context;
    protected List<T> _list;

    public FragmentPageAdapter(@NonNull FragmentActivity fragmentActivity, Context context, List<T> list) {
        super(fragmentActivity);
        this.context = context;
        this._list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return _list.get(position);
    }

    @Override
    public int getItemCount() {
        return _list.size();
    }

    public void deleteItem(int position) {
        _list.remove(position);
        this.notifyDataSetChanged();
    }

    public void add(List<T> list) {
        _list.addAll(list);
        this.notifyDataSetChanged();
    }

    public void replaceItem(int position , T t){
        _list.set(position, t);
        this.notifyDataSetChanged();
    }

    public void clearAll() {
        _list.clear();
        this.notifyDataSetChanged();
    }

    public void refresh(List<T> list) {
        this._list = list;
        this.notifyDataSetChanged();
    }

    public void refresh(){
        this.notifyDataSetChanged();
    }

}
