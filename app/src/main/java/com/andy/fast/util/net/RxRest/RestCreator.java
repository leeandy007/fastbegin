package com.andy.fast.util.net.RxRest;

import com.andy.fast.util.net.config.ConfigKeys;
import com.andy.fast.util.net.config.NetConfig;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreator {

    private static final class RetrofitHolder {
        private static final String BASE_URL = NetConfig.getConfiguration(ConfigKeys.API_HOST);
        //创建全局的Retrofit客户端
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OKHttpHolder.getClient())
                .build();
    }

    private static final class OKHttpHolder {
        private static final HttpLoggingInterceptor LOGGING_INTERCEPTOR = NetConfig.getConfiguration(ConfigKeys.LOGGING_INTERCEPTOR);
        private static final ArrayList<Interceptor> INTERCEPTORS = NetConfig.getConfiguration(ConfigKeys.INTERCEPTOR);
        private static final ArrayList<ConnectionSpec> CONNECTION_SPECS = NetConfig.getConfiguration(ConfigKeys.CONNECTION_SPEC);
        private static final SocketFactory SOCKET_FACTORY = NetConfig.getConfiguration(ConfigKeys.SOCKET_FACTORY);
        private static final SSLSocketFactory SSL_SOCKET_FACTORY = NetConfig.getConfiguration(ConfigKeys.SSL_SOCKET_FACTORY);
        private static final X509TrustManager X_509_TRUST_MANAGER = NetConfig.getConfiguration(ConfigKeys.X_509_TRUST_MANAGER);
        private static final int TIME_OUT = 60;

        //创建全局的OKHttp客户端
        private static final OkHttpClient getClient() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
            if (LOGGING_INTERCEPTOR != null) {
                builder.addNetworkInterceptor(LOGGING_INTERCEPTOR);
            }
            if (INTERCEPTORS != null) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    builder.addInterceptor(interceptor);
                }
            }
            if (CONNECTION_SPECS != null) {
                builder.connectionSpecs(CONNECTION_SPECS);
            }
            if (SOCKET_FACTORY != null) {
                builder.socketFactory(SOCKET_FACTORY);
            }
            if (SSL_SOCKET_FACTORY != null && X_509_TRUST_MANAGER != null) {
                builder.sslSocketFactory(SSL_SOCKET_FACTORY, X_509_TRUST_MANAGER);
            }
            return builder.build();
        }
    }

    //提供接口让调用者得到retrofit对象
    private static final class RxRestServiceHolder {
        private static final RxRestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    public static RxRestService getRxRestService() {
        return RxRestServiceHolder.REST_SERVICE;
    }


}
