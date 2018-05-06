package com.andy.fast.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @Desc Toast工具类
 */
public class ToastUtil {

    private static ToastUtil _instance;

    private ToastUtil() {}

    public static ToastUtil obtain(){
        if(_instance == null){
            synchronized (ToastUtil.class) {
                if (_instance == null) {
                    _instance = new ToastUtil();
                }
            }
        }
        return _instance;
    }

    public void Short(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void Short(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public void Long(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public void Long(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }




}
