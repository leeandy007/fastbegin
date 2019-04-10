package com.andy.fast.util.net.RxRest;

import com.andy.fast.enums.HttpMethod;
import com.andy.fast.util.net.body.UploadRequestBody;
import com.andy.fast.util.net.listener.UploadProgressListener;
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

    private final Map<String, String> HEADERS;

    private final Map<String, Object> PARAMS;

    private final UploadProgressListener uploadProgressListener;

    public RxRestClient(String url,
                        Map<String, String> headers,
                        Map<String, Object> params,
                        UploadProgressListener uploadProgressListener) {
        this.URL = url;
        this.HEADERS = headers;
        this.PARAMS = params;
        this.uploadProgressListener = uploadProgressListener;
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

    public final Observable<String> uploadProgress(){
        return request(HttpMethod.UPLOAD_PROGRESS);
    }

    public final Observable<ResponseBody> download(){
        return RestCreator.getRxRestService().download(URL, HEADERS);
    }

    private Observable<String> request(HttpMethod method){
        final RxRestService service = RestCreator.getRxRestService();
        switch (method) {
            case GET:
                return service.get(URL, HEADERS, PARAMS);
            case POST:
                return service.post(URL, HEADERS, PARAMS);
            case PUT:
                return service.put(URL, HEADERS, PARAMS);
            case DELETE:
                return service.delete(URL, HEADERS, PARAMS);
            case UPLOAD:
                return service.uploadMore(URL, HEADERS, getUploadMore(PARAMS));
            case UPLOAD_PROGRESS:
                return service.uploadMore(URL, HEADERS, getUploadProgress(PARAMS));
            case POST_RAW:
                return service.postRaw(URL, HEADERS, getRawBody(PARAMS));
        }
        return null;
    }

    private Map<String, RequestBody> getUploadMore(Map<String, Object> map) {
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

    private RequestBody getRawBody(Map<String, Object> params){
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(params));
        return requestBody;
    }

    private Map<String, RequestBody> getUploadProgress(Map<String, Object> map) {
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
            UploadRequestBody body = new UploadRequestBody(RequestBody.create(MultipartBody.FORM, entry.getValue()), this.uploadProgressListener);
            resultMap.put("file\"; filename=\""+entry.getKey(), body);
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), entry.getValue()==null?"":valueOf(entry.getValue()));
            resultMap.put(entry.getKey(), body);
        }
        return resultMap;
    }

}
