package com.andy.fast.presenter.base;

import com.andy.fast.view.IView;

import java.lang.ref.WeakReference;

//表示层
public class BasePresenter<T extends IView> {

    //弱引用
    protected WeakReference<T> viewReference;

    protected T mView;

    private T getView(){
        return viewReference.get();
    }

    public void onAttach(T view){
        viewReference = new WeakReference<T>(view);
        if(null != viewReference && null != viewReference.get()){
            mView = getView();
        }

    }

    public void onDetach(){
        viewReference.clear();
    }

}
