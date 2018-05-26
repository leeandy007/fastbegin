package com.andy.fast.util.net;

import android.graphics.Bitmap;

public interface ImageCallback {

    void onSuccess(Bitmap bitmap);

    void onFailure(String errorMessage);

}
