package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.andy.fast.util.bus.Bus;

import butterknife.ButterKnife;

public abstract class BaseCheckedHolder<T>{

	protected CheckBox mCheckBox;
	protected int checkedResId;

	public BaseCheckedHolder(int checkedResId) {
		this.checkedResId = checkedResId;
	}

	public View CreateView(Context context){
		View view = View.inflate(context, getLayout(), null);
		ButterKnife.bind(this, view);
		Bus.obtain().register(this);
		return view;
	}

	public abstract int getLayout();

	public void initView(View view) {
		mCheckBox = view.findViewById(checkedResId);
	}

	public abstract void initData(Context context, T t, int position, BaseCheckedAdapter<T>.CheckedBean checked);

}
