package com.andy.fast.ui.activity.sub;

import android.content.Context;
import android.os.Bundle;

import com.andy.fast.R;
import com.andy.fast.bean.DataBean;
import com.andy.fast.presenter.sub.MainPresenter;
import com.andy.fast.ui.activity.base.BaseActivity;
import com.andy.fast.util.ToastUtil;
import com.andy.fast.view.activity.MainView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity<MainView, MainPresenter<MainView>> implements MainView {

    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    protected int getLayout(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter<MainView> CreatePresenter() {
        return new MainPresenter<>();
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
        ToastUtil.obtain().Short(this, bean.getReason());

    }


    @Override
    public void loadView() {

    }

    @Override
    public void showToast(String message) {
        ToastUtil.obtain().Short(this, message);
    }

}
