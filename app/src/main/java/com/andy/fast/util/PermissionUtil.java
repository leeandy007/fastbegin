package com.andy.fast.util;

import android.content.Context;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

public class PermissionUtil {

    public static void requestPermission(Context context, int requestCode, PermissionListener listener, String... permission) {
        AndPermission.with(context).requestCode(requestCode).permission(permission).callback(listener).start();
    }

}