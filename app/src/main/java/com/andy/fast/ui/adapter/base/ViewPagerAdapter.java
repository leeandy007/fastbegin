package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.os.Parcelable;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.fast.util.bus.Bus;

import java.util.List;

public abstract class ViewPagerAdapter<T> extends PagerAdapter {

	protected List<T> _list;
	protected Context context;
	protected OnItemClickListener<T> mOnItemClickListener;
	
	public ViewPagerAdapter(Context context, List<T> list, OnItemClickListener<T> onItemClickListener) {
		this.context = context;
		this._list = list;
		this.mOnItemClickListener = onItemClickListener;
	}
	
	public List<T> getList() {
		return _list;
	}
	
	public T getItem(int position) {
		return _list.get(position);
	}
	
	@Override
	public int getCount() {
		return _list.size();
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
		viewGroup.removeView((View) object);
	}
	
	@Override
	public Object instantiateItem(ViewGroup viewGroup, final int position) {
		final View view = CreateView(viewGroup);
		initView(view);
		initData(context, getItem(position), position);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mOnItemClickListener != null){
					mOnItemClickListener.onItemClick(view, getItem(position), position);
				}
			}
		});
		viewGroup.addView(view);
		return view;
	}

	protected View CreateView(ViewGroup viewGroup){
		View view = LayoutInflater.from(context).inflate(getLayout(), viewGroup, false);
		Bus.obtain().register(this);
		return view;
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

	public void add(List<T> list) {
		_list.addAll(list);
		this.notifyDataSetChanged();
	}

	public void refresh(List<T> list) {
		this._list = list;
		notifyDataSetChanged();
	}

	public void refresh(){
		this.notifyDataSetChanged();
	}

	protected abstract int getLayout();

	protected void initView(View view){}

	protected abstract void initData(Context context, T t, int position);

}