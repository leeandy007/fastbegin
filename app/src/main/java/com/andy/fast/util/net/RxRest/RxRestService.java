package com.andy.fast.util.net.RxRest;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RxRestService {

    @GET
    Observable<String> get(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> params);

    @DELETE
    Observable<String> delete(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @HeaderMap Map<String, String> headers);

    @Multipart
    @POST
    Observable<String> upload(@Url String url, @HeaderMap Map<String, String> headers, @Part MultipartBody.Part file);

    @Multipart
    @POST
    Observable<String> uploadMore(@Url String url, @HeaderMap Map<String, String> headers, @PartMap Map<String, RequestBody> params);

    @POST
    Observable<String> postRaw(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody body);

    @PUT
    Observable<String> putRaw(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody body);
}
