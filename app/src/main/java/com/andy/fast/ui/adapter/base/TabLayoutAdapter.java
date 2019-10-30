package com.andy.fast.ui.adapter.base;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * Created by leeandy007 on 2017/9/5.
 */

public class TabLayoutAdapter extends FragmentAdapter {

    private List<String> titles;

    public TabLayoutAdapter(FragmentManager fm, Context context, List<Fragment> list, List<String> titles) {
        super(fm, context, list);
        this.titles = titles;
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position % titles.size());
    }

}
