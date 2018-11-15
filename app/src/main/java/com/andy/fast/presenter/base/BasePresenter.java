package com.andy.fast.presenter.base;

import com.andy.fast.model.base.IModel;
import com.andy.fast.util.bus.Bus;
import com.andy.fast.view.IView;

import java.lang.ref.WeakReference;

/**
 * 表示层基类
 * */
public abstract class BasePresenter<V extends IView, M extends IModel> {

    /**
     * 弱引用
     * */
    private WeakReference<V> viewReference;


    /**
     * view对象
     */
    protected V mView;

    /**
     * modle对象
     */
    protected M model;

    /**
     * @return 返回View对象
     */
    private V getView(){
        return viewReference.get();
    }


    /**
     * @param view 绑定
     */
    public void onAttach(V view){
        viewReference = new WeakReference<>(view);
        if(null != viewReference && null != viewReference.get()){
            model = initModelImpl();
            mView = getView();
            Bus.obtain().register(this);
        }
    }

    /**
     * 解绑
     */
    public void onDetach(){
        viewReference.clear();
        Bus.obtain().unregister(this);
    }

    /**
     * 初始化model
     */
    protected abstract M initModelImpl();

}
