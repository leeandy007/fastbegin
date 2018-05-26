package com.andy.fast.util.net;

import java.util.Map;

public interface NetProcessor {

    void get(String url, Map<String, Object> params, Callback callback);

    void post(String url, Map<String, Object> params, Callback callback);

    void getBitmap(String url, ImageCallback callback);

}
