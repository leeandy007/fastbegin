package com.andy.fast.view;

import android.content.Context;

public interface IView {

    Context getContext();

    void loadView();

    void showToast(String message);

}
