package com.andy.fast.util.net;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.annotation.NonNull;
//网络监听
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {

    private String TAG;

    public NetworkCallbackImpl(String tag) {
        this.TAG = tag;
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        Log.i(TAG,"网络已连接");
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        Log.i(TAG,"网络已断开");
    }

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i(TAG,"onCapabilitiesChanged: WiFi已经连接");
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i(TAG,"onCapabilitiesChanged: 蜂窝网络已经连接");
            } else {
                Log.i(TAG,"onCapabilitiesChanged: 其他网络");
            }
        }
    }
}
