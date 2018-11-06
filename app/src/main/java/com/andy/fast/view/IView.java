package com.andy.fast.view;

import android.content.Context;

import com.andy.fast.enums.ToastMode;

public interface IView {


    /**
     * @return 返回上下文
     */
    Context getContext();

    /**
     * 加载等待
     */
    void loadView();

    /**
     * @param mode 显示模式
     * @param message 显示提示信息
     */
    void showToast(ToastMode mode, String message);

}
