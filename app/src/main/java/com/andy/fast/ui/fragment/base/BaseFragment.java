package com.andy.fast.ui.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.fast.enums.ToastMode;
import com.andy.fast.presenter.base.BasePresenter;
import com.andy.fast.util.ToastUtil;
import com.andy.fast.util.bus.Bus;
import com.andy.fast.view.IView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Describe
 * @Author leeandy007
 * @Date 2016-9-2 下午2:05:15
 */
public abstract class BaseFragment<V extends IView, P extends BasePresenter> extends Fragment {

	//上下文
	protected Context _context;
	//交换层
	protected P presenter;
	//回调
	protected FragmentCallBack mCallBack;
	//butter
	protected Unbinder unbinder;

	protected Integer page = 1;

	/**
	 * Activity取Fragment所传递的值时调用的回调接口
	 */
	public interface FragmentCallBack {

		/**
		 * @param param Object...变参多个不固定个数不规定类型的返回结果
		 * @DESC Activity中调用取出Fragment中的值
		 **/
		void setResult(Object... param);

	}

	public BaseFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//初始化上下文
		_context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(getLayout(savedInstanceState), null);
		//初始化交换层
		presenter = CreatePresenter();
		//绑定交换层的生命周期
		presenter.onAttach((V)this);
		//初始化butter
		unbinder = ButterKnife.bind(this, v);
		//注册Bus
		Bus.obtain().register(this);
		initView(v);
		initData();
		return v;
	}

	/**
	 * 初始化布局
	 */
	protected abstract int getLayout(Bundle savedInstanceState);

	/**
	 * 初始化交换层
	 * */
	protected abstract P CreatePresenter();

	/**
	 * 初始化控件
	 */
	protected void initView(View v){};

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 声明Fragment实例，所创建的回调接口必须要在Activity中实现
	 */
	@Override
	public void onAttach(Activity activity) {
		try {
			mCallBack = (FragmentCallBack) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentCallBack");
		}
		super.onAttach(activity);
	}

	/**
	 * 声明Fragment实例，所创建的回调接口必须要在Activity中实现
	 */
	@Override
	public void onAttach(Context context) {
		try {
			mCallBack = (FragmentCallBack) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString()
					+ " must implement FragmentCallBack");
		}
		super.onAttach(context);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == Activity.RESULT_OK) {
			doActivityResult(requestCode, intent);
		}
	}

	/**
	 * 带返回值跳转的数据的处理方法
	 */
	protected void doActivityResult(int requestCode, Intent intent) {};


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		//解绑交换层
		presenter.onDetach();
		//解绑butter
		unbinder.unbind();
		//解绑Bus
		Bus.obtain().unregister(this);
	}

	/**
	 * @Desc Toast
	 * */
	public void showToast(ToastMode mode, String message){
		switch (mode){
			case SHORT:
				ToastUtil.obtain().Short(_context, message);
				break;
			case LONG:
				ToastUtil.obtain().Long(_context, message);
				break;
		}
	}
}
