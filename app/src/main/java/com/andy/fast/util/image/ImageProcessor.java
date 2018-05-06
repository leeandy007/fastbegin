package com.andy.fast.util.image;

import android.content.Context;
import android.widget.ImageView;

public interface ImageProcessor {

    void load(Context context, String url, ImageView imageView, int defaultImage, int errorImage);

}
