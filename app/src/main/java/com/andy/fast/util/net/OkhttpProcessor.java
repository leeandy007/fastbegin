package com.andy.fast.util.net;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.String.valueOf;

public class OkhttpProcessor implements NetProcessor {

    private OkHttpClient client = null;
    private Handler handler = null;

    public OkhttpProcessor() {
        client = new OkHttpClient();
        handler = new Handler();
    }

    @Override
    public void get(String url, Map<String, Object> params, final Callback callback) {
        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(result);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(response.message().toString());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void post(String url, Map<String, Object> params, final Callback callback) {
        final Map<String, Object> map = new HashMap<>();
        final Map<String, File> files = new HashMap<>();
        for(Map.Entry<String, Object> entry : params.entrySet()){
            if(entry.getValue() instanceof File){
                files.put(entry.getKey(), (File)entry.getValue());
            } else {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        if(files == null && files.isEmpty()){
            //单纯的表单提交
            postForm(url, map, callback);
        } else {
            //带参数上传图片
            postFile(url, map, files, callback);
        }

    }

    @Override
    public void getBitmap(String url, final ImageCallback callback) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(bitmap);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(response.message().toString());
                        }
                    });
                }
            }
        });
    }

    private void postForm(String url, Map<String, Object> params, final Callback callback){
        RequestBody requestBody = appendBody(params);
        postBack(url, requestBody, callback);
    }

    private void postFile(String url, Map<String, Object> map, Map<String, File> files, final Callback callback){
        RequestBody requestBody = appendBodyFiles(map, files);
        postBack(url, requestBody, callback);
    }

    private void postBack(String url, RequestBody requestBody, final Callback callback){
        Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(result);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(response.message().toString());
                        }
                    });
                }
            }
        });
    }

    /**
     * 拼接form表单提交参数
     */
    private RequestBody appendBody(Map<String, Object> params) {
        FormBody.Builder body = new FormBody.Builder();
        if (null == params || params.isEmpty()) {
            return body.build();
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            body.add(valueOf(entry.getKey()), valueOf(entry.getValue()));
        }
        return body.build();
    }

    /**
     * 拼接form表单提交参数
     */
    private RequestBody appendBodyFiles(Map<String, Object> params, Map<String, File> files) {
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (null != files && !files.isEmpty()) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                RequestBody requestBody = RequestBody.create(MultipartBody.FORM, entry.getValue());
                // 参数分别为， 请求key ，文件名称 ， RequestBody
                body.addFormDataPart("file", entry.getKey(), requestBody);
            }
        }
        if (null != params && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                body.addFormDataPart(valueOf(entry.getKey()), entry.getValue()==null?"":valueOf(entry.getValue()));
            }
        }
        return body.build();
    }

}
