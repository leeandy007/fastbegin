package com.andy.fast.util.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import java.io.File;

public interface ImageCallback {

    void load(Context context, String url, ImageView imageView, int defaultImage, int errorImage);

    void load(Context context, Integer resId, ImageView imageView, int defaultImage, int errorImage);

    void load(Context context, File file, ImageView imageView, int defaultImage, int errorImage);

    void load(Context context, Uri uri, ImageView imageView, int defaultImage, int errorImage);

    void pause(Context context);

    void resume(Context context);

}
