package com.andy.fast.util.net.interceptor;

import com.andy.fast.util.net.listener.DownloadProgressListener;
import com.andy.fast.util.net.body.DownloadResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class DownloadInterceptor implements Interceptor {

    private DownloadProgressListener downloadProgressListener;

    public DownloadInterceptor(DownloadProgressListener downloadProgressListener) {
        this.downloadProgressListener = downloadProgressListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new DownloadResponseBody(originalResponse.body(), downloadProgressListener))
                .build();
    }
}
