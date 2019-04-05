package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class FragmentAdapter<T extends Fragment> extends FragmentPagerAdapter {

	protected Context context;
	protected List<T> _list;
	protected FragmentManager fm;
	
	public FragmentAdapter(FragmentManager fm , Context context, List<T> list) {
		super(fm);
		this.fm = fm;
		this.context = context;
		this._list = list;
	}
	

	@Override
	public T getItem(int position) {
		return _list.get(position);
	}

	@Override
	public int getCount() {
		return _list.size();
	}

	/**
	 * 隐藏fragment
	 * */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		T t = getItem(position);
		fm.beginTransaction().hide(t).commitAllowingStateLoss();
	}

	/**
	 * 显示fragment
	 * */
	@Override
	public T instantiateItem(ViewGroup container, int position) {
		T t =  (T) super.instantiateItem(container, position);
		fm.beginTransaction().show(t).commitAllowingStateLoss();
		return t;
	}

    public void deleteItem(int position) {
		_list.remove(position);
        this.notifyDataSetChanged();
    }

    public void add(List<T> beans) {
		_list.addAll(beans);
        this.notifyDataSetChanged();
    }

    public void replaceBean(int position , T t){
		_list.set(position, t);
        this.notifyDataSetChanged();
    }

    public void clearAll() {
		_list.clear();
        this.notifyDataSetChanged();
    }

}
