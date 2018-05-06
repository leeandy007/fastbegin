package com.andy.fast.common;

import android.app.Application;

import com.andy.fast.util.net.NetProxy;
import com.andy.fast.util.net.VolleyProcessor;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetProxy.init(new VolleyProcessor(this));

    }

}
