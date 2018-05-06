package com.andy.fast.model.base;

public interface IModel {

    interface CallBackListener<T>{

        void onSuccess(T result);

        void onFailure(String errorMsg);
    }

}
