package com.andy.fast.view;

import android.content.Context;

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
     * @param message 显示提示信息
     */
    void showToast(String message);

}
