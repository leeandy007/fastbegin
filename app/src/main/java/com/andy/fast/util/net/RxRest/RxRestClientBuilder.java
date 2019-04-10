package com.andy.fast.util.net.RxRest;

import com.andy.fast.util.net.listener.UploadProgressListener;

import java.util.Map;

public class RxRestClientBuilder {

    private String mUrl;

    private Map<String, String> mHeaders;

    private Map<String, Object> mParams;

    private UploadProgressListener mUploadProgressListener;

    RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder headers(Map<String, String> headers){
        this.mHeaders = headers;
        return this;
    }

    public final RxRestClientBuilder params(Map<String, Object> param){
        this.mParams = param;
        return this;
    }

    public final RxRestClientBuilder uploadListener(UploadProgressListener uploadProgressListener){
        this.mUploadProgressListener = uploadProgressListener;
        return this;
    }

    public final RxRestClient build(){
        return new RxRestClient(mUrl, mHeaders, mParams, mUploadProgressListener);
    }
}
