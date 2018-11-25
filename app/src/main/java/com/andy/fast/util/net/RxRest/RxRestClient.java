package com.andy.fast.util.net.RxRest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static java.lang.String.valueOf;

public class RxRestClient {

    private final String URL;

    private final Map<String, Object> PARAMS;

    public RxRestClient(String url,
                        Map<String, Object> params) {
        this.URL = url;
        this.PARAMS = params;
    }

    public static RxRestClientBuilder create(){
        return new RxRestClientBuilder();
    }

    public final Observable<String> get(){
        return request(HttpMethod.GET);
    }

    public final Observable<String> post(){
        return request(HttpMethod.POST);
    }

    public final Observable<String> put(){
        return request(HttpMethod.PUT);
    }

    public final Observable<String> delete(){
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload(){
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download(){
        return RestCreator.getRxRestService().download(URL, PARAMS);
    }

    private Observable<String> request(HttpMethod method){
        final RxRestService service = RestCreator.getRxRestService();
        switch (method) {
            case GET:
                return service.get(URL, PARAMS);
            case POST:
                return service.post(URL, PARAMS);
            case PUT:
                return service.put(URL, PARAMS);
            case DELETE:
                return service.delete(URL, PARAMS);
            case UPLOAD:
                return uploadMore(service, URL, PARAMS);
        }
        return null;
    }

    public Observable<String> uploadMore(RxRestService service, String url, Map<String, Object> params) {
        final Map<String, RequestBody> resultMap;
        final Map<String, Object> map = new HashMap<>();
        final Map<String, File> files = new HashMap<>();
        for(Map.Entry<String, Object> entry : params.entrySet()){
            if(entry.getValue() instanceof File){
                files.put(entry.getKey(), (File)entry.getValue());
            } else {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        resultMap = appendBodyFiles(map, files);
        Observable<String> call = service.uploadMore(url, resultMap);
        return call;
    }


    /**
     * 拼接form表单提交参数
     */
    private Map<String, RequestBody> appendBodyFiles(Map<String, Object> map, Map<String, File> files) {
        Map<String, RequestBody> resultMap = new HashMap<>();
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (null != files && !files.isEmpty()) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                RequestBody requestBody = RequestBody.create(MultipartBody.FORM, entry.getValue());
                // 参数分别为， 请求key ，文件名称 ， RequestBody
                body.addFormDataPart("file", entry.getKey(), requestBody);
                resultMap.put(entry.getKey(), body.build());
            }
        }
        if (null != map && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                body.addFormDataPart(valueOf(entry.getKey()), entry.getValue()==null?"":valueOf(entry.getValue()));
                resultMap.put(entry.getKey(), body.build());
            }
        }
        return resultMap;
    }

}
