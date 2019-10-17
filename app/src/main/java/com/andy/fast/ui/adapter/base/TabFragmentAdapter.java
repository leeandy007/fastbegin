package com.andy.fast.ui.adapter.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by leeandy007 on 2017/9/5.
 */

public class TabFragmentAdapter extends FragmentAdapter {

    private List<String> titles;

    public TabFragmentAdapter(FragmentManager fm, Context context, List<Fragment> list, List<String> titles) {
        super(fm, context, list);
        this.titles = titles;
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position % titles.size());
    }

}
