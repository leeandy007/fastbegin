package com.andy.fast.model.sub.imodel;

import com.andy.fast.model.base.IModel;

import java.util.Map;

public interface MainModel extends IModel {

    void LoadData(Map map, CallBackListener callBackListener);


}
