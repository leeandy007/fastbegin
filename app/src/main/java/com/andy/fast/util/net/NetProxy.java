package com.andy.fast.util.net;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

public class NetProxy implements NetProcessor {

    private static NetProxy _instance;
    private static NetProcessor mNetProcessor = null;
    private static String mBaseUrl;
    private Map<String, Object> params;


    public static void init(NetProcessor netProcessor, String baseUrl){
        mNetProcessor = netProcessor;
        mBaseUrl = baseUrl;
    }

    private NetProxy(){
        params = new HashMap<>();
    }

    public static NetProxy obtain(){
        synchronized (NetProxy.class){
            if(null == _instance){
                _instance = new NetProxy();
            }
        }
        return _instance;
    }

    @Override
    public void get(String urlAction, Map<String, Object> params, Callback callback) {
        final String finalUrl = appendParams(urlAction, params);
        mNetProcessor.get(finalUrl, params, callback);
    }

    @Override
    public void post(String urlAction, Map<String, Object> params, Callback callback) {
        final String finalUrl = mBaseUrl + urlAction;
        mNetProcessor.post(finalUrl, params, callback);
    }

    @Override
    public void getBitmap(String url, ImageCallback callback) {
        mNetProcessor.getBitmap(url, callback);
    }


    /**
     * 自动组装请求参数
     * */
    private String appendParams(String urlAction, Map<String, Object> params) {
        StringBuffer stringBuffer = new StringBuffer(mBaseUrl);
        stringBuffer.append(urlAction);
        if(params.isEmpty() || params == null){
            return stringBuffer.toString();
        }
        // 存储封装好的请求体信息
        if(stringBuffer.indexOf("?") <= 0){
            stringBuffer.append("?");
        } else {
            if(!stringBuffer.toString().endsWith("?")){
                stringBuffer.append("&");
            }
        }
        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                stringBuffer.append(valueOf(entry.getKey())).append("=").append(URLEncoder.encode(entry.getValue()==null?"":valueOf(entry.getValue()),"utf-8")).append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1); // 删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

}
