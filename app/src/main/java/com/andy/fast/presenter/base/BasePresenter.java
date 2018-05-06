package com.andy.fast.presenter.base;

import com.andy.fast.view.IView;

import java.lang.ref.WeakReference;

//表示层
public class BasePresenter<T extends IView> {

    //弱引用
    protected WeakReference<T> iView;

    public void onAttach(T view){
        iView = new WeakReference<T>(view);
    }

    public void onDetach(){
        iView.clear();
    }

}
