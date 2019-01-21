package com.andy.fast.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.andy.fast.R;

/**
 * Created by leeandy007 on 2017/7/6.
 */

public class IntentUtil {

    public enum Method {
        GET,
        POST
    }

    /**
     * @param clszz  目标页面
     * @param bundle 传值载体
     * @Desc 正常页面跳转
     */
    public static void startActivity(Context context, Class clszz, Bundle bundle, String title) {
        Intent intent = new Intent(context, clszz);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("title", title);
        intent.putExtras(bundle);
        context.startActivity(intent);
        animNext(context);
    }

    /**
     * @param clszz       目标页面
     * @param bundle      传值
     * @param requestCode 请求码
     * @Desc 带返回值跳转
     */
    public static void startActivityForResult(Context context, Class clszz, Bundle bundle, String title, int requestCode) {
        Intent intent = new Intent(context, clszz);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("title", title);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);
        animNext(context);
    }

//    /**
//     * @Desc 正常页面跳转
//     * @param bundle 传值载体
//     * */
//    public static void startWebActivity(Method method, Context context, Bundle bundle){
//        Intent intent = null;
//        switch (method){
//            case GET:
//                intent = new Intent(context, WebViewGetActivity.class);
//                break;
//            case POST:
//                intent = new Intent(context, WebViewPostActivity.class);
//                break;
//        }
//        if(bundle != null){
//            intent.putExtras(bundle);
//        }
//        context.startActivity(intent);
//        animNext(context);
//    }

    /**
     * @Desc 页面跳转动画
     */
    public static void animNext(Context context) {
        /**<<<------右入左出*/
        ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
