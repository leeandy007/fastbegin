package com.andy.fast.model.sub.modelImpl;

import com.andy.fast.bean.DataBean;
import com.andy.fast.model.sub.imodel.MainModel;
import com.andy.fast.util.net.NetCallback;
import com.andy.fast.util.net.NetProxy;

import java.util.Map;

public class MainModelImpl implements MainModel {

    @Override
    public void LoadData(Map map, final CallBackListener callBackListener) {
        NetProxy.obtain().get("jztk/query", map, new NetCallback<DataBean>() {
            @Override
            public void onFailure(String errorMsg) {
                callBackListener.onFailure(errorMsg);
            }

            @Override
            public void onSuccess(DataBean dataBean) {
                callBackListener.onSuccess(dataBean);
            }
        });


    }
}
