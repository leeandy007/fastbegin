package com.andy.fast.util.net;

public interface Callback {

    void onSuccess(String result);

    void onFailure(String errorMsg);

}
