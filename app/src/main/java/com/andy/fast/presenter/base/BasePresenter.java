package com.andy.fast.presenter.base;

import com.andy.fast.view.IView;

import java.lang.ref.WeakReference;

/**
 * 表示层基类
 * */
public class BasePresenter<T extends IView> {

    /**
     * 弱引用
     * */
    protected WeakReference<T> viewReference;


    /**
     * view对象
     */
    protected T mView;

    /**
     * @return 返回View对象
     */
    private T getView(){
        return viewReference.get();
    }


    /**
     * @param view 绑定
     */
    public void onAttach(T view){
        viewReference = new WeakReference<T>(view);
        if(null != viewReference && null != viewReference.get()){
            mView = getView();
        }
    }

    /**
     * 解绑
     */
    public void onDetach(){
        viewReference.clear();
    }

}
