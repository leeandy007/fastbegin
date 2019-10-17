package com.andy.fast.ui.adapter.base;

import android.view.View;

public interface OnItemClickLitener<T> {

    void onItemClick(View view, T t, int position);

}
