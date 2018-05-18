package com.andy.fast.ui.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andy.fast.R;
import com.andy.fast.presenter.base.BasePresenter;
import com.andy.fast.util.bus.Bus;
import com.andy.fast.view.IView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<V extends IView,T extends BasePresenter<V>> extends AppCompatActivity {

    /**
     * 上下文
     * */
    protected Context _context;
    /**
     * 交换层
     * */
    protected T presenter;
    /**
     * butter
     * */
    protected Unbinder unbinder;

    protected Integer page = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化上下文
        _context = getContext();
        //初始化布局
        setContentView(getLayout(savedInstanceState));
        //初始化交换层
        presenter = CreatePresenter();
        //绑定交换层的生命周期
        presenter.onAttach((V)this);
        //注册butter
        unbinder = ButterKnife.bind(this);
        //注册Bus
        Bus.obtain().register(this);
        //初始化数据
        initData();
    }

    /**
     * 初始化上下文
     * */
    public abstract Context getContext();

    /**
     * 初始化布局
     * */
    protected abstract int getLayout(Bundle savedInstanceState);

    /**
     * 初始化交换层
     * */
    protected abstract T CreatePresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑交换层
        presenter.onDetach();
        //解绑butter
        unbinder.unbind();
        //解绑Bus
        Bus.obtain().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            //带返回值跳转的数据的处理方法
            doActivityResult(requestCode, intent);
        }
    }

    /**
     * 初始化数据
     * */
    protected abstract void initData();

    /**
     * 带返回值跳转的数据的处理方法
     * */
    protected void doActivityResult(int requestCode, Intent intent){};

    /**
     * @Desc 返回键操作
     */
    @Override
    public void onBackPressed() {
        this.finish();
        animBack();
    }

    /**
     * @Desc 页面返回动画
     * */
    protected void animBack(){
        /**------>>>左入右出*/
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
