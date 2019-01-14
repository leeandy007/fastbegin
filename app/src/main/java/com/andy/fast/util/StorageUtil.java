package com.andy.fast.util;

import android.os.Environment;

import java.io.File;

public class StorageUtil {

    /**
     * 判断sdcard是否存在
     * @return
     */
    public static boolean sdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String createFolder(String path){
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

}
