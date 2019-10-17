package com.andy.fast.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.andy.fast.ui.adapter.base.BaseInfoAdapter;
import com.andy.fast.ui.adapter.base.BaseRecyclerViewAdapter;

import java.util.List;

public class PageUtil {

    public interface PageRecyclerListener<T extends BaseRecyclerViewAdapter>{

        /**
         * 初始化RecyclerView的适配器
         * @return 返回RecyclerView的适配器
         */
        T createAdapter();
    }

    public interface PageListener<T extends BaseInfoAdapter>{
        /**
         * 初始化listView的适配器
         * @return 返回listView的适配器
         */
        T createAdapter();
    }

    /**
     * @param context 上下文
     * @param list 数据
     * @param recyclerView recyclerView
     * @param adapter 适配器
     * @param page 当前页
     * @param listener 分页监听
     */
    public static void page(Context context, List list, RecyclerView recyclerView, BaseRecyclerViewAdapter adapter, Integer page, PageRecyclerListener listener){
        if (page == 1) {
            if (!StringUtil.isEmpty(list)) {
                if (null == adapter) {
                    adapter = listener.createAdapter();
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.clearAll();
                    adapter.add(list);
                }
            } else {
                if(null != adapter){
                    adapter.clearAll();
                }
                ToastUtil.obtain().Short(context,"暂无数据");
            }
        } else {
            if (!StringUtil.isEmpty(list)) {
                adapter.add(list);
            } else {
                ToastUtil.obtain().Short(context,"数据加载完毕");
            }
        }
    }

    /**
     * @param context 上下文
     * @param list 数据
     * @param listView listview
     * @param adapter 适配器
     * @param page 当前页
     * @param listener 分页监听
     */
    public static void page(Context context, List list, ListView listView, BaseInfoAdapter adapter, Integer page, PageListener listener){
        if (page == 1) {
            if (!StringUtil.isEmpty(list)) {
                if (null == adapter) {
                    adapter = listener.createAdapter();
                    listView.setAdapter(adapter);
                } else {
                    adapter.clearAll();
                    adapter.add(list);
                }
            } else {
                if(null != adapter){
                    adapter.clearAll();
                }
                ToastUtil.obtain().Short(context,"暂无数据");
            }
        } else {
            if (!StringUtil.isEmpty(list)) {
                adapter.add(list);
            } else {
                ToastUtil.obtain().Short(context,"数据加载完毕");
            }
        }
    }
}
