package com.andy.fast.util.net.RxRest;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
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

    public final Observable<String> postRaw(){
        return request(HttpMethod.POST_RAW);
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
                return service.uploadMore(URL, getUploadMore(PARAMS));
            case POST_RAW:
                return service.postRaw(URL, getRawBody(PARAMS));
        }
        return null;
    }

    public Map<String, RequestBody> getUploadMore(Map<String, Object> map) {
        final Map<String, RequestBody> resultMap = new HashMap<>();
        final Map<String, Object> params = new HashMap<>();
        final Map<String, File> files = new HashMap<>();
        for(Map.Entry<String, Object> entry : map.entrySet()){
            if(entry.getValue() instanceof File){
                files.put(entry.getKey(), (File)entry.getValue());
            } else {
                params.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<String, File> entry : files.entrySet()) {
            RequestBody body = RequestBody.create(MultipartBody.FORM, entry.getValue());
            resultMap.put("file\"; filename=\""+entry.getKey(), body);
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), entry.getValue()==null?"":valueOf(entry.getValue()));
            resultMap.put(entry.getKey(), body);
        }
        return resultMap;
    }

    public RequestBody getRawBody(Map<String, Object> params){
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(params));
        return requestBody;
    }

}
