package com.andy.fast.ui.adapter.base;

/**
 * Created by leeandy007 on 2017/6/26.
 */

public interface ViewHoldersCreator<ViewHolder> {

    ViewHolder createHolder(int viewType);

    int initViewType(int position);

}
