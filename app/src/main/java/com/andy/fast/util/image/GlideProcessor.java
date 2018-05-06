package com.andy.fast.util.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideProcessor implements ImageProcessor{
    @Override
    public void load(Context context, String url, ImageView imageView, int defaultImage, int errorImage) {
        Glide.with(context).load(url).dontAnimate().centerCrop().placeholder(defaultImage).error(errorImage).crossFade().into(imageView);
    }
}
