package com.andy.fast.util.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Proxy;

public class Image {

    private static Image _instance;
    private static ImageCallback callback;

    private Image() {}

    //在Application中初始化
    public static void init(ImageCallback call){
        callback = (ImageCallback) Proxy.newProxyInstance(call.getClass().getClassLoader(), call.getClass().getInterfaces(), new ImageHandler(call));
    }

    //获取单例对象
    public static Image obtain(){
        synchronized (Image.class){
            if(null == _instance){
                _instance = new Image();
            }
        }
        return _instance;
    }

    public void load(Context context, Object obj, ImageView imageView){
        load(context, obj, imageView, 0, 0);
    }

    public void load(Context context, Object obj, ImageView imageView, int defaultImage, int errorImage){
        if(obj instanceof String){
            callback.load(context, (String) obj, imageView, defaultImage, errorImage);
        }
        if(obj instanceof Integer){
            callback.load(context, (Integer) obj, imageView, defaultImage, errorImage);
        }
        if(obj instanceof File){
            callback.load(context, (File) obj, imageView, defaultImage, errorImage);
        }
        if(obj instanceof Uri){
            callback.load(context, (Uri) obj, imageView, defaultImage, errorImage);
        }
    }

    public void pause(Context context){
        callback.pause(context);
    }

    public void resume(Context context){
        callback.resume(context);
    }


}
