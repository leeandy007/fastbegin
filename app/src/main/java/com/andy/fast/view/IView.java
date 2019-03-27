package com.andy.fast.view;

import android.content.Context;
import android.view.View;

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
     * 关闭加载等待
     */
    void hideView();

    /**
     * @param mode 显示模式
     * @param message 显示提示信息
     */
    void showToast(ToastMode mode, String message);


    /**
     * 点击事件处理
     * @param view
     */
    void onViewClicked(View view);

}
