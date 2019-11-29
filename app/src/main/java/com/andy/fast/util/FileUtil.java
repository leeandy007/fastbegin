package com.andy.fast.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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

    /**
     * 获取文件大小
     * @param size 文件长度
     * @return String 文件大小
     */
    public static String fileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    /**
     * 从Asset文件读取JSON
     * @param context 上下文
     * @param fileName 文件名称
     * @return String 返回Json
     */
    public static String getJsonFromAsset(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * 获取视频文件截图
     * @param path 视频文件的路径
     * @return width 返回视频的宽
     */
    public static int getVideoWidth(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap.getWidth();
    }

    /**
     * 获取视频文件截图
     * @param path 视频文件的路径
     * @return height 返回视频的高
     */
    public static int getVideoHeight(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        Bitmap bitmap = media.getFrameAtTime();
        return  bitmap.getHeight();
    }

}
