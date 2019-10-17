package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public abstract class ViewPagerAdapter<T> extends PagerAdapter {

	protected List<T> list;
	protected View view;
	protected Context context;
	protected int resId;

	public ViewPagerAdapter(List<T> list) {
		this.list = list;
	}
	
	public ViewPagerAdapter(Context context, List<T> list, int resId) {
		this.context = context;
		this.list = list;
		this.resId = resId;
	}
	
	public List<T> getList() {
		return list;
	}
	
	public Object getItem(int position) {
		return list.get(position);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public Parcelable saveState() {
		return super.saveState();
	}
	
	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
		super.restoreState(state, loader);
	}

	@Override
	public void startUpdate(ViewGroup viewGroup) {
		super.startUpdate(viewGroup);
	}
	
	@Override
	public void finishUpdate(ViewGroup viewGroup) {
		super.finishUpdate(viewGroup);
	}

	@Override
	public void destroyItem(ViewGroup viewGroup, int position, Object object) {
		removeItem(viewGroup, position, object);
	}
	
	/**
	 * 销毁View
	 * */
	public abstract void removeItem(ViewGroup viewGroup, int position, Object object);
	
	@Override
	public Object instantiateItem(ViewGroup viewGroup, int position) {
		return dealView(context, list, resId, position, viewGroup, view);
	}
	
	/**
	 * 处理业务
	 * */
	public abstract View dealView(Context context, List<T> list, int resId,
								  int position, ViewGroup viewGroup, View view);

}