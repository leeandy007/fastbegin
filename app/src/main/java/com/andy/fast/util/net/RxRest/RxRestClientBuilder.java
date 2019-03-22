package com.andy.fast.util.net.RxRest;

import java.util.Map;

public class RxRestClientBuilder {

    private String mUrl;

    private Map<String, String> mHeaders;

    private Map<String, Object> mParams;

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

    public final RxRestClient build(){
        return new RxRestClient(mUrl, mHeaders, mParams);
    }
}
