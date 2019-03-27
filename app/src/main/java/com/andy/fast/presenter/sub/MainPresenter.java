package com.andy.fast.presenter.sub;

import com.andy.fast.bean.DataBean;
import com.andy.fast.enums.ToastMode;
import com.andy.fast.model.base.IModel;
import com.andy.fast.model.sub.imodel.MainModel;
import com.andy.fast.model.sub.modelImpl.MainModelImpl;
import com.andy.fast.presenter.base.BasePresenter;
import com.andy.fast.view.activity.MainView;

import java.util.Map;

public class MainPresenter extends BasePresenter<MainView, MainModel> {

    @Override
    protected MainModel initModelImpl() {
        return new MainModelImpl();
    }

    public void fetch(Map map){
        mView.loadView();
        model.LoadData(map, new IModel.CallBackListener<DataBean>() {
            @Override
            public void onSuccess(DataBean result) {
                mView.getData(result);
            }

            @Override
            public void onFailure(String errorMsg) {
                mView.showToast(ToastMode.SHORT, errorMsg);
            }

        });
    }


}
