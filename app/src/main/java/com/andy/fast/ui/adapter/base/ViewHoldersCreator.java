package com.andy.fast.ui.adapter.base;

import android.view.ViewGroup;

/**
 * Created by leeandy007 on 2017/6/26.
 */

public interface ViewHoldersCreator<ViewHolder> {

    ViewHolder createHolder(ViewGroup parent, int viewType);

    int initViewType(int position);

}
