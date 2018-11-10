package com.andy.fast.util.net.RxRest;

import com.andy.fast.util.net.config.ConfigKeys;
import com.andy.fast.util.net.config.NetConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreator {

    private static final class RetrofitHolder{
        private static final String BASE_URL = NetConfig.getConfiguration(ConfigKeys.API_HOST);
        //创建全局的Retrofit客户端
        private  static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OKHttpHolder.OKHTTP_CLIENT)
                .build();
    }

    private static final class OKHttpHolder{
        private static final int TIME_OUT = 60;
        //创建全局的OKHttp客户端
        private static final OkHttpClient OKHTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    //提供接口让调用者得到retrofit对象
    private static final class RxRestServiceHolder{
        private static final RxRestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    public static RxRestService getRxRestService(){
        return RxRestServiceHolder.REST_SERVICE;
    }


}
