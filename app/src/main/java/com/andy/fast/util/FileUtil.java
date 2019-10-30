/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.andy.fast.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;

import java.io.File;

public class FileUtil {
    /**
     * 判断sdcard是否存在
     * @return
     */
    public static boolean sdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 创建文件夹
     * @return
     */
    public static String createFolder(String path){
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), System.currentTimeMillis() + ".jpg");
        return file;
    }

    /**
     * 获取文件uri
     * @return uri
     */
    public static Uri getFileUri(Context context, File file) {
        Uri uri;
        //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(context, context.getPackageName()+".fileprovider", file);
        } else {
            //7.0以下
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}
