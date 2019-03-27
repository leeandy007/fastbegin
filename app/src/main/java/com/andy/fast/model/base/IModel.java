package com.andy.fast.model.base;

import com.andy.fast.bean.BaseResult;

public interface IModel {


    /**
     * @param <T> 回调返回结果<泛型是返回的处理对象>
     */
    interface CallBackListener<T extends BaseResult>{

        void onSuccess(T result);

        void onFailure(String errorMsg);
    }

}
