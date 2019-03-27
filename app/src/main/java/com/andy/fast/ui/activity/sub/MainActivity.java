package com.andy.fast.ui.activity.sub;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.andy.fast.R;
import com.andy.fast.bean.DataBean;
import com.andy.fast.presenter.sub.MainPresenter;
import com.andy.fast.ui.activity.base.BaseActivity;
import com.andy.fast.view.activity.MainView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {

    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    protected int getLayout(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter CreatePresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initData() {
        String url = "http://v.juhe.cn/jztk/query?subject=1&model=c1&testType=&=&key=08b71a6e8398e19496629d830527d543";
        Map<String, Object> map = new HashMap<>();
        map.put("subject", 1);
        map.put("model", "c1");
        map.put("testType", "");
        map.put("key", "08b71a6e8398e19496629d830527d543");
        presenter.fetch(map);
    }


    @Override
    public void getData(DataBean bean) {

    }


    @Override
    public void loadView() {

    }

    @Override
    public void hideView() {

    }

    @Override
    public void onViewClicked(View view) {

    }

}
