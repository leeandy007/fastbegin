package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author leeandy007 一级多选列表
 * @param <T>
 */
public abstract class BaseCheckedAdapter<T> extends BaseInfoAdapter<T> {

	protected HashMap<String, CheckedBean> checkedItem;
	protected ArrayList<String> checkedList;

	public BaseCheckedAdapter(List<T> list, ArrayList<String> checkedList, ViewHolderCreator mViewHolderCreator) {
		super(list, mViewHolderCreator);
		this.checkedList = checkedList;
		initCheckedItem(list);
	}

	/**
	 * 返回选中的Item的index
	 */
	public HashMap<String, Object> getCheckedItems() {
		if (!checkedItem.isEmpty()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			Set<String> set = checkedItem.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String id = it.next();
				if (checkedItem.get(id).isChecked()) {
					map.put(id, checkedItem.get(id).getObject());
				}
			}
			return map;
		}
		return null;
	}

	/**
	 * 全选
	 */
	public void checkedAll() {
		Set<String> set = checkedItem.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String index = it.next();
			CheckedBean logic = checkedItem.get(index);
			logic.setChecked(true);
		}
		this.notifyDataSetChanged();
	}
	
	/**
	 * 全部取消
	 */
	public void cancleAll() {
		Set<String> set = checkedItem.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String index = it.next();
			CheckedBean logic = checkedItem.get(index);
			logic.setChecked(false);
		}
		this.notifyDataSetChanged();
	}

	/**
	 * 反选
	 */
	public void inverseAll() {
		Set<String> set = checkedItem.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String index = it.next();
			CheckedBean logic = checkedItem.get(index);
			if (logic.isChecked()) {
				logic.setChecked(false);
			} else {
				logic.setChecked(true);
			}
		}
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseCheckedHolder holder = null;
		if (convertView == null) {
			holder = (BaseCheckedHolder) mViewHolderCreator.createHolder();
            convertView = holder.CreateView(parent.getContext());
			holder.initView(convertView);
			convertView.setTag(holder);
		} else {
			holder = (BaseCheckedHolder) convertView.getTag();
		}
		setItemView(parent.getContext(), holder, position, convertView);
		return convertView;
	}

	/**
	 * 初始化Item的选中状态
	 */
	protected abstract void initCheckedItem(List<T> list);

	/**
	 * 点击事件处理
	 * @param adapterView
	 * @param view
	 * @param position
	 * @param id
	 */
	public abstract void onItemClick(AdapterView<?> adapterView, View view,
									 int position, long id);



	/**
	 * 设置自定义数据部分
	 * @param holder
	 * @param position
	 * @param convertView
	 */
	public abstract void setItemView(Context context, BaseCheckedHolder<T> holder, int position,
			View convertView);

	/**
	 * 
	 * @author Administrator 选中逻辑Bean
	 */
	public class CheckedBean {

		private boolean checked;
		private T object;

		public CheckedBean(boolean checked) {
			this.checked = checked;
		}

		public CheckedBean(boolean checked, T object) {
			this.checked = checked;
			this.object = object;
		}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		public T getObject() {
			return object;
		}

		public void setObject(T object) {
			this.object = object;
		}
	}

}
