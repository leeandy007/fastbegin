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
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RxRestService {

    @Headers({"UrlName:BaseUrl"})
    @GET
    Observable<String> get(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    @Headers({"UrlName:BaseUrl"})
    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> params);

    @Headers({"UrlName:BaseUrl"})
    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> params);

    @Headers({"UrlName:BaseUrl"})
    @DELETE
    Observable<String> delete(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> params);

    @Headers({"UrlName:BaseUrl"})
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @HeaderMap Map<String, String> headers);

    @Headers({"UrlName:BaseUrl"})
    @Multipart
    @POST
    Observable<String> upload(@Url String url, @HeaderMap Map<String, String> headers, @Part MultipartBody.Part file);

    @Headers({"UrlName:BaseUrl"})
    @Multipart
    @POST
    Observable<String> uploadMore(@Url String url, @HeaderMap Map<String, String> headers, @PartMap Map<String, RequestBody> params);

    @Headers({"UrlName:BaseUrl"})
    @POST
    Observable<String> postRaw(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody body);

    @Headers({"UrlName:BaseUrl"})
    @PUT
    Observable<String> putRaw(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody body);
}
