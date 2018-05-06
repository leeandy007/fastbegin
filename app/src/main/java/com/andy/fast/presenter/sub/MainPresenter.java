package com.andy.fast.presenter.sub;

import com.andy.fast.model.base.IModel;
import com.andy.fast.bean.DataBean;
import com.andy.fast.model.sub.imodel.MainModel;
import com.andy.fast.model.sub.modelImpl.MainModelImpl;
import com.andy.fast.presenter.base.BasePresenter;
import com.andy.fast.view.IView;
import com.andy.fast.view.activity.MainView;

import java.util.Map;

public class MainPresenter<T extends IView> extends BasePresenter<MainView> {


    private MainModel mainModel = new MainModelImpl();

    public void fetch(Map map){
        iView.get().loadView();
        mainModel.LoadData(map, new IModel.CallBackListener<DataBean>() {
            @Override
            public void onSuccess(DataBean result) {
                iView.get().getData(result);
            }

            @Override
            public void onFailure(String errorMsg) {
                iView.get().showError(errorMsg);
            }

        });
    }

}
