package com.andy.fast.ui.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.andy.fast.R;
import com.andy.fast.enums.ToastMode;
import com.andy.fast.presenter.base.BasePresenter;
import com.andy.fast.util.ToastUtil;
import com.andy.fast.util.ViewUtil;
import com.andy.fast.util.bus.Bus;
import com.andy.fast.view.IView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;

public abstract class BaseActivity<V extends IView, P extends BasePresenter> extends AppCompatActivity {

    /**
     * 上下文
     * */
    protected Context _context;
    /**
     * 交换层
     * */
    protected P presenter;
    /**
     * butter
     * */
    protected Unbinder unbinder;

    protected Integer page = 1;

    protected GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化上下文
        _context = getContext();
        //全局手势
        gestureDetector = new GestureDetector(_context, onGestureListener);
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
    protected abstract P CreatePresenter();

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

    /**
     * @Desc Toast
     * */
    public void showToast(ToastMode mode, String message){
        switch (mode){
            case SHORT:
                ToastUtil.obtain().Short(_context, message);
                break;
            case LONG:
                ToastUtil.obtain().Long(_context, message);
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int width = ViewUtil.screenWidth(_context);
        if(ev.getX() > 50 && Math.abs(ev.getX()-width) > 50){
            insideTouch(ev);
            return super.dispatchTouchEvent(ev);
        } else {
            return onTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * 在此实例化OnGestureListener监听的实例
     */
    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1,
                               MotionEvent e2,
                               float velocityX,
                               float velocityY) {
            if(e1 == null || e2 == null){
                return false;
            }
            // e1就是初始状态的MotionEvent对象，e2就是滑动了过后的MotionEvent对象
            // velocityX和velocityY就是滑动的速率
            float x = e2.getX() - e1.getX();//滑动后的x值减去滑动前的x值 就是滑动的横向水平距离(x)
            float y = e2.getY() - e1.getY();//滑动后的y值减去滑动前的y值 就是滑动的纵向垂直距离(y)
            //如果滑动的横向距离大于100，表明是右滑了，那么就执行下面的方法
            if (x > 100 && e1.getX() < 50) {
                doResult(RIGHT);
            }
            int width = ViewUtil.screenWidth(_context);
            //如果滑动的横向距离大于100，表明是左滑了(因为左滑为负数，所以距离大于100就是x值小于-100)
            if (x < -100 && Math.abs(e1.getX()-width) < 50) {
                doResult(LEFT);
            }
            return true;
        }
    };

    public void doResult(int action) {
        switch (action) {
            case RIGHT:
                onBackPressed();
                break;
            case LEFT:
                onBackPressed();
                break;
        }
    }

    protected void insideTouch(MotionEvent ev){};

}
