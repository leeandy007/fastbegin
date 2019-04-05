package com.andy.fast.ui.adapter.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author leeandy007
 * @Desc: 一级列表适配器基类
 * @version :
 * 
 */
public abstract class BaseInfoAdapter<T> extends BaseAdapter {

	protected ViewHolderCreator mViewHolderCreator;
	protected List<T> _list;

	public BaseInfoAdapter(List<T> list, ViewHolderCreator mViewHolderCreator) {
		this._list = list;
		this.mViewHolderCreator = mViewHolderCreator;
	}
	
	public List<T> getList() {
		return _list;
	}

	@Override
	public int getCount() {
		return _list.size();
	}

	@Override
	public T getItem(int position) {
		return _list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseInfoViewHolder holder = null;
		if(convertView == null){
			holder = (BaseInfoViewHolder) mViewHolderCreator.createHolder();
			convertView = holder.CreateView(parent.getContext());
			holder.initView(convertView);
			convertView.setTag(holder);
		} else {
			holder = (BaseInfoViewHolder) convertView.getTag();
		}
		holder.initData(parent.getContext(), getItem(position), position);
		return convertView;
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
