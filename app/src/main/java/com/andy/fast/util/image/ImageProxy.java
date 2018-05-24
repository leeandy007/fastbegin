package com.andy.fast.util.image;


import android.content.Context;
import android.widget.ImageView;

public class ImageProxy implements ImageProcessor {

    private static ImageProxy _instance;
    private static ImageProcessor mImageProcessor;

    private ImageProxy() {}

    public static void init(ImageProcessor imageProcessor){
        mImageProcessor = imageProcessor;
    }

    public static ImageProxy obtain(){
        synchronized (ImageProxy.class){
            if(null == _instance){
                _instance = new ImageProxy();
            }
        }
        return _instance;
    }


    @Override
    public void load(Context context, String url, ImageView imageView, int defaultImage, int errorImage) {
        mImageProcessor.load(context, url, imageView, defaultImage, errorImage);
    }

    @Override
    public void pause(Context context) {
        mImageProcessor.pause(context);
    }

    @Override
    public void resume(Context context) {
        mImageProcessor.resume(context);
    }
}
